package com.example.Student;

import io.restassured.RestAssured;
import net.serenitybdd.rest.SerenityRest;
import org.junit.BeforeClass;

public class BaseAPITest {
    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8080/api";
        RestAssured.basePath = "/projects";
    }
}
