package com.github.dsmiles.utils;

import com.github.dsmiles.constants.ApiConstants;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class AuthHelper {

    private AuthHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static String authenticate(String username, String password) {
        JSONObject authPayload = new JSONObject();
        authPayload.put("username", username);
        authPayload.put("password", password);

        Response response = given()
            .contentType(ContentType.JSON)
            .body(authPayload.toString())
            .when()
            .post(ApiConstants.AUTH_URL);

        response.then().statusCode(200);
        String jwtToken = response.jsonPath().getString("access_token");
        assertNotNull(jwtToken, "JWT token should not be null");

        return jwtToken;
    }
}
