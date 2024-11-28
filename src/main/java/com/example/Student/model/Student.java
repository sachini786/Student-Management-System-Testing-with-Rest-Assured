package com.example.Student.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format") // Ensures email adheres to RFC 5322
    @Size(max = 100, message = "Email must not exceed 100 characters") // Optional size constraint
    @Column(name = "email", nullable = false, unique = true) // Ensure unique constraint at DB level
    private String email;

    @NotNull(message = "Index number is required")
    @Min(value = 1, message = "Index number should be at least 1") // Minimum value
    @Max(value = 99999999, message = "Index number should not exceed 99999999") // Maximum value
    @Column(name = "index_number", nullable = false)
    private Integer indexNumber;

    @Past(message = "Date of birth must be in the past")
    @Column(name = "date_of_birth", nullable = true)
    private LocalDate dateOfBirth;

}
