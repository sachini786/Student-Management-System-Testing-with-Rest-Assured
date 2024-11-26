package com.example.Student.controller;

import com.example.Student.exception.ResourceNotFondException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.Student.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Student.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
@Tag(name = "Project API", description = "Manage project data for students")
public class ProjectController {

	@Autowired
	private ProjectRepository projectRepository;

	@Operation(summary = "Create a new project for a student")
	@PostMapping("/create")
	public ResponseEntity<Project> createProjectForStudent(@RequestBody Project project) {
		if (project.getProjectName() == null || project.getStudentId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		Project savedProject = projectRepository.save(project);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
	}
	@Operation(summary = "Get all projects by student ID")
	@GetMapping("/student/{student_Id}")
	public ResponseEntity<List<Project>> getProjectsByStudentId(@PathVariable Integer student_Id) {
		List<Project> projects = projectRepository.findByStudentId(student_Id);
		if (projects.isEmpty()) {
			throw new ResourceNotFondException("No projects found for student with ID: " + student_Id) {
			};
		}
		return ResponseEntity.ok(projects);
	}
	@Operation(summary = "Get a project by its ID")
	@GetMapping("/{project_id}")
	public ResponseEntity<Project> getProjectById(@PathVariable Integer project_id) {
		Optional<Project> project = projectRepository.findById(project_id);
		if (project.isPresent()) {
			return ResponseEntity.ok(project.get());
		} else {
			throw new ResourceNotFondException("Project not found with ID: " + project_id);
		}
	}

	@Operation(summary = "Update a project")
	@PutMapping("/{project_id}")
	public ResponseEntity<Project> updateProject(@PathVariable Integer project_id, @RequestBody Project projectDetails) {
		Project existingProject = projectRepository.findById(project_id)
				.orElseThrow(() -> new ResourceNotFondException("Project not found with ID: " + project_id));

		existingProject.setProjectName(projectDetails.getProjectName());
		existingProject.setStudentId(projectDetails.getStudentId());

		Project updatedProject = projectRepository.save(existingProject);
		return ResponseEntity.ok(updatedProject);
	}

	@Operation(summary = "Delete a project")
	@DeleteMapping("/{project_id}")
	public ResponseEntity<HttpStatus> deleteProject(@PathVariable Integer project_id) {
		Project project = projectRepository.findById(project_id)
				.orElseThrow(() -> new ResourceNotFondException("Project not found with ID: " + project_id));

		projectRepository.delete(project);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
