package com.github.dsmiles;

import com.github.dsmiles.constants.ApiConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CLIVersionEndpointTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = ApiConstants.BASE_URI;
    }

    @Test
    @DisplayName("Test retrieving CLI version")
    public void testGetCLIVersion() {
        given()
            .contentType(ContentType.JSON)
            .when()
            .log().all()
            .get("/cli-version")
            .then()
            .log().all()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("version", equalTo("1.0.1"));  // Assuming the response contains a "version" field
    }
}
