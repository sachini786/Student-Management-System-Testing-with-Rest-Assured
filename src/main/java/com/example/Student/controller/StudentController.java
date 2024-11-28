package com.example.Student.controller;

import com.example.Student.exception.ResourceNotFondException;
import com.example.Student.model.ErrorResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.Student.model.Student;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.Student.repository.StudentRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student API", description = "Manage student data")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Operation(summary = "Get all students")
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Operation(summary = "Get student by student_id")
    @GetMapping(value = "/{student_id}", produces = "application/json")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer student_id) {
        Optional<Student> student = studentRepository.findById(student_id);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            throw new ResourceNotFondException("Student not found with ID: " +student_id );
        }
    }

    @Operation(summary = "Create a new student")
    @PostMapping("/student")
    public ResponseEntity<?> createStudent(@Valid @RequestBody Student student, BindingResult result) {
        // Validate input using BindingResult
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage) // Get validation error messages
                    .collect(Collectors.joining(", ")); // Join messages into a single string
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errors);
        }

        // Check for duplicate email
        if (studentRepository.existsByEmail(student.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Email already exists: " + student.getEmail());
        }

        // Save the valid student
        Student savedStudent = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }


    @Operation(summary = "Update an existing student")
    @PutMapping("{student_id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Integer student_id, @RequestBody Student studentDetail) {
        Student updatedStudent = studentRepository.findById(student_id)
                .orElseThrow(() -> new ResourceNotFondException("Student not found with ID: " + student_id));

        updatedStudent.setEmail(studentDetail.getEmail());
        updatedStudent.setIndexNumber(studentDetail.getIndexNumber());
        updatedStudent.setDateOfBirth(studentDetail.getDateOfBirth());

        studentRepository.save(updatedStudent);
        return ResponseEntity.ok(updatedStudent);
    }
    @Operation(summary = "Delete a student")
    @DeleteMapping("{student_id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable Integer student_id) {
        Student student = studentRepository.findById(student_id)
                .orElseThrow(() -> new ResourceNotFondException("Student not found with ID: " + student_id));
        studentRepository.delete(student);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}