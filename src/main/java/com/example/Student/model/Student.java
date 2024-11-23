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
@Table(name = "student") // Matches the table name in the database
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer  student_Id;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull(message = "Index number is required")
    @Min(value = 1, message = "Index number should be at least 1")
    @Max(value = 99999999, message = "Index number should not exceed 99999999")
    @Column(name = "indexNumber", nullable = false)
    private Integer indexNumber;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
}
