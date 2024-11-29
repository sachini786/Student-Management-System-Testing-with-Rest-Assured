package com.example.Student;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProjectTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api/projects";
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
                .log().all()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/create")
                .then()
                .log().all()
                .statusCode(201)
                .body(containsString("Project successfully created"));

    }
    @Test
    public void createProjectWithMissingRequiredFields() {
        // Create a request body with missing required fields
        String requestBody = "{ \"studentId\": 1 }"; // Missing projectName

        given()
                .log().all()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/create")
                .then()
                .log().all()
                .statusCode(400)
                .body(equalTo("Project name and student ID must be provided."));
    }
    @Test
    public void getProjectByIdValid() {
        int project_id = 1;

        given()
                .log().all()
                .header("Content-Type", "application/json")
                .when()
                .get(String.valueOf(project_id))
                .then()
                .log().all()
                .statusCode(200);

    }
    @Test
    public void getProjectByIdInvalid() {
        int project_id = 99999;

        given()
                .log().all()
                .header("Content-Type", "application/json")
                .when()
                .get(String.valueOf(project_id))
                .then()
                .log().all()
                .statusCode(404);

    }

    @Test
    public void updateProjectValidId() {
        int project_id = 1;
        String requestBody = "{\"projectName\": \"Payment management system\", \"studentId\": 1}";

        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put("/" + project_id)
                .then()
                .statusCode(200)
                .body("projectName.trim()", equalTo("Payment management system"));
    }
    @Test
    public void updateProjectInvalidId() {
        int project_id = 99999;
        String requestBody = "{\"projectName\": \"Payment management system\", \"studentId\": 99999}";

        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put(String.valueOf(project_id))
                .then()
                .statusCode(404);

    }
    @Test
    public void updateProjectMissingRequiredFields() {
        int project_id = 1;

        String requestBody = "{\"studentId\": 1}";

        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put(String.valueOf(project_id))
                .then()
                .statusCode(404);

    }
    @Test
    public void deleteProjectValidId() {
        int project_id = 2;

        given()
                .header("Content-Type", "application/json")
                .when()
                .delete(String.valueOf(project_id))
                .then()
                .statusCode(204);
    }
    @Test
    public void deleteProjectInvalidId() {
        int project_id = 99999;

        given()
                .header("Content-Type", "application/json")
                .when()
                .delete(String.valueOf(project_id))
                .then()
                .statusCode(404) ;
    }
    @Test
    public void createProjectInvalidInput() {

        String requestBody = "{\"projectName\": \"payment management system\", \"studentId\": \"sachini\"}";

        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/create")
                .then()
                .statusCode(400) ;
    }
    @Test
    public void getProjectCountForStudentId1() {
        int studentId = 1;
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(String.valueOf(studentId))
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(3))
                .body("size()", equalTo(3));
    }
    @Test
    public void getProjectsForInvalidStudentId() {
        int studentId = 999;

        given()
                .header("Content-Type", "application/json")
                .when()
                .get(String.valueOf(studentId))
                .then()
                .statusCode(404);

    }
    @Test
    public void getStudentByProjectId() {
        int project_id = 1;


        int studentId = given()
                .header("Content-Type", "application/json")
                .when()
                .get(String.valueOf(project_id))
                .then()
                .statusCode(200)
                .extract()
                .path("studentId");

        // Print the student ID
        System.out.println("The student ID is: " + studentId);
    }
    @Test
    public void getStudentByInvalidProjectId() {
        int project_id = 99999;

        given()
                .header("Content-Type", "application/json")
                .when()
                .get(String.valueOf(project_id))
                .then()
                .statusCode(404);
    }

}
