package com.example.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.Student.repository.ProjectRepository;
import com.example.Student.repository.StudentRepository;

@SpringBootApplication
public class StudentApplication {

	private final StudentRepository studentRepository;
	private final ProjectRepository projectRepository;

	@Autowired
	public StudentApplication(StudentRepository studentRepository, ProjectRepository projectRepository) {
		this.studentRepository = studentRepository;
		this.projectRepository = projectRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(StudentApplication.class, args);
	}
}
