package com.example.Student;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import net.serenitybdd.junit.runners.SerenityRunner;


@RunWith(SerenityRunner.class)
public class ProjectAPITest extends BaseAPITest {

    @Test
    public void getProjectByIdValid() {
        int projectId = 1;


        Response response = given()
                .pathParam("project_id", projectId)
                .when()
                .get("/{project_id}")
                .then()
                .statusCode(200)
                .extract().response();

        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);
    }

}
