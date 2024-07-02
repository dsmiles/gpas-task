package com.github.dsmiles;

import com.github.dsmiles.constants.ApiConstants;
import com.github.dsmiles.utils.AuthHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AdminEndpointTests {

    private static String jwtToken;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = ApiConstants.BASE_URL;
        // username and password should be set by Secrets Manager or other secure means
        jwtToken = AuthHelper.authenticate("your_username", "your_password");
    }

    @Test
    @DisplayName("Test retrieving organisation info")
    public void testGetAdminInfo() {
        String organisation_uid = "497f6eca-6276-4993-bfeb-53cbbbba6f08";      // copied from API doc

        given()
            .header("Authorization", "Bearer " + jwtToken)
            .when()
            .pathParam("org_id", organisation_uid)
            .get(ApiConstants.ORGANISATION_URL + "/{org_id}")
            .then()
            .statusCode(200)
            .body("name", notNullValue())
            .body("description", notNullValue())
            .body("is_sharing", notNullValue())
            .body("portal_upload_enabled", notNullValue())
            .body("ont_enabled", notNullValue())
            .body("has_consented", notNullValue())
            .body("id", equalTo( "497f6eca-6276-4993-bfeb-53cbbbba6f08"))
            .body("created_at", equalTo("2019-08-24T14:15:22Z"));

        // Could check response against a JSON sample file or POJO
    }

    @Test
    @DisplayName("Test creating a new user")
    public void testCreateUser() {
        // Could use a POJO to create test data
        JSONObject userPayload = new JSONObject();
        userPayload.put("name", "string");
        userPayload.put("description", "string");
        userPayload.put("is_sharing", false);
        userPayload.put("portal_upload_enabled", false);
        userPayload.put("ont_enabled", false);
        userPayload.put("has_consented", false);

        given()
            .header("Authorization", "Bearer " + jwtToken)
            .contentType(ContentType.JSON)
            .body(userPayload.toString())
            .when()
            .post(ApiConstants.CREATE_USER_URL)
            .then()
            .statusCode(201) // Assuming the creation of a user returns 201 Created
            .contentType(ContentType.JSON)
            .body("name", equalTo("string"))
            .body("description", equalTo("string"))
            .body("is_sharing", equalTo(false))
            .body("portal_upload_enabled", equalTo(false))
            .body("ont_enabled", equalTo(false))
            .body("has_consented", equalTo(false))
            .body("id", notNullValue()); // Assuming the response contains an ID field

        // Could check response against a JSON sample file or POJO
    }
}
