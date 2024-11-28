package com.example.Student;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class StudentApplicationTests {

	@BeforeAll
	static void setup() {
		RestAssured.baseURI = "http://localhost:8080/api/students";
	}

	@Test
	void testCreateStudent() {
		String requestBody = """
            {
                "email": "sachinifon8@gmail.com",
                "indexNumber": 1273636,
                "dateOfBirth": "2000-04-01"
            }
        """;

		given()
				.header("Content-Type", "application/json")
				.body(requestBody)
				.when()
				.post("/student")
				.then()
				.statusCode(201)
				.body("student_Id", notNullValue())
				.body("email", equalTo("sachinifon8@gmail.com"))
				.body("indexNumber", equalTo(1273636))
				.body("dateOfBirth", equalTo("2000-04-01"));
	}
	@Test
	void testCreateStudentWithExistingEmail() {
		String duplicateStudent = """
        {
          "email": "sachinifon8@gmail.com",
          "indexNumber": 654321,
          "dateOfBirth": "1999-12-31"
        }
    """;

		given()
				.header("Content-Type", "application/json")
				.body(duplicateStudent)
				.when()
				.post("/student")
				.then()
				.log().body() // Debug response body if needed
				.statusCode(409) // Expecting 409 Conflict
				.body(containsString("Email already exists: sachinifon8@gmail.com"));
	}
	@Test
	void testCreateStudentWithMissingFields(){
		String duplicateStudent = """
        {
          "indexNumber": 6512721,
          "dateOfBirth": "1999-10-31"
        }
    """;

		given()
				.header("Content-Type", "application/json")
				.body(duplicateStudent)
				.when()
				.post("/student")
				.then()
				.log().body()
				.statusCode(400)
				.body(containsString("Email is required and cannot be null or empty."));
	}
	@Test
	void updateDetail() {
		String requestBody = """
        {
            "email": "sachinifon8@gmail.com",
            "indexNumber": 2765243,
            "dateOfBirth": "2000-04-01"
        }
    """;

		int studentId = 1;

		given()
				.header("Content-Type", "application/json")
				.body(requestBody)
				.when()
				.put(String.valueOf(studentId))
				.then()
				.log().body()
				.statusCode(200);
	}

	@Test
	void updateDetailWithInvalidStudentId() {
		String requestBody = """
        {
            "email": "sachinifon8@gmail.com",
            "indexNumber": 2765243,
            "dateOfBirth": "2000-04-01"
        }
    """;

		int studentId = 999; // Assuming 999 is not in the database

		given()
				.header("Content-Type", "application/json")
				.body(requestBody)
				.when()
				.put(String.valueOf(studentId))
				.then()
				.log().body()
				.statusCode(404);
	}
	@Test
	void getStudentById() {
		int studentId = 1;

		given()
				.header("Content-Type", "application/json")
				.when()
				.get(String.valueOf(studentId))
				.then()
				.log().body()
				.statusCode(200)
				.body("student_Id", equalTo(studentId)); // Ensure the field name matches the response JSON
	}

	@Test
	void getStudentByIdNotFound() {
		int studentId = 123;

		given()
				.header("Content-Type", "application/json")
				.when()
				.get(String.valueOf(studentId))
				.then()
				.log().body()
				.statusCode(404);

	}

}

