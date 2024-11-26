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
}
