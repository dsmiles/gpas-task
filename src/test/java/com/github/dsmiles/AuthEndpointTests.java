package com.github.dsmiles;

import com.github.dsmiles.constants.ApiConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthEndpointTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = ApiConstants.BASE_URI;
    }

    @Test
    @DisplayName("Test successful authentication")
    public void testSuccessfulAuthentication() {
        JSONObject authPayload = new JSONObject();
        // username and password should be set by Secrets Manager or other secure means
        authPayload.put("username", "your_username");
        authPayload.put("password", "your_password");

        given()
            .contentType(ContentType.JSON)
            .body(authPayload.toString())
            .when()
            .log().all()
            .post(ApiConstants.AUTH_URL)
            .then()
            .log().all()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("access_token", notNullValue())
            .body("expires_in", notNullValue())
            .body("token_type", notNullValue())
            .body("scope", notNullValue());
    }

    @Test
    @DisplayName("Test authentication with invalid credentials")
    public void testAuthenticationInvalidCredentials() {
        JSONObject authPayload = new JSONObject();
        authPayload.put("username", "invalid_username");
        authPayload.put("password", "invalid_password");

        given()
            .contentType(ContentType.JSON)
            .body(authPayload.toString())
            .when()
            .log().all()
            .post(ApiConstants.AUTH_URL)
            .then()
            .log().all()
            .statusCode(401) // Assuming 401 Unauthorized for invalid credentials
            .body("message", equalTo("Invalid username or password"));

//        Actually getting an HTTP 403 Forbidden with "message": "Internal Server Error"
//        Looks like the usual 401/403 mismatch. For example:
//        If a user tries to create a file without logging in, they should get a 401 error
//        If a user tries to create a file while logged in but has no permission to create files in a particular folder, they should get a 403 error
    }

    @Test
    @DisplayName("Test authentication with missing credentials")
    public void testAuthenticationMissingCredentials() {
        JSONObject authPayload = new JSONObject();
        authPayload.put("username", "your_username");

        given()
            .contentType(ContentType.JSON)
            .body(authPayload.toString())
            .when()
            .log().all()
            .post(ApiConstants.AUTH_URL)
            .then()
            .log().all()
            .statusCode(422) // 422 Unprocessable Content
            .body("detail[0].type", equalTo("missing"))
            .body("detail[0].loc[0]", equalTo("body"))
            .body("detail[0].loc[1]", equalTo("password"))
            .body("detail[0].msg", equalTo("Field required"))
            .body("detail[0].input.username", equalTo("your_username"));

        // Could check response against a JSON sample file or POJO
    }
}
