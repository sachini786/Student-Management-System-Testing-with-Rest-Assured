package com.example.Student;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class ProjectTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080; // Ensure this matches your Spring Boot application port
        RestAssured.basePath = "/api/projects"; // Adjusted to match the actual endpoint
    }

    @Test
    void testCreateProject() {
        String requestBody = """
            {
                "projectName": " management system",
                "studentId": 1
            }
        """;

        given()
                .log().all() // Log request details for debugging
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/create") // Ensure the path matches your controller
                .then()
                .log().all() // Log response details for debugging
                .statusCode(201) // Adjust expected status code based on your controller
                .body(containsString("Project successfully created"));

    }
    @Test
    public void createProjectWithMissingRequiredFields() {
        // Create a request body with missing required fields
        String requestBody = "{ \"studentId\": 1 }"; // Missing projectName

        given()
                .log().all() // Log request details for debugging
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/create") // Ensure this path matches your controller's endpoint
                .then()
                .log().all() // Log response details for debugging
                .statusCode(400) // Expected status code for bad request
                .body(equalTo("Project name and student ID must be provided.")); // Expected error message
    }

}
