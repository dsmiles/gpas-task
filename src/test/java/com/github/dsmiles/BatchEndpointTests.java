package com.github.dsmiles;

import com.github.dsmiles.constants.ApiConstants;
import com.github.dsmiles.utils.AuthHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.dsmiles.constants.ApiConstants.BATCHES_URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BatchEndpointTests {

    private static String jwtToken;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = ApiConstants.BASE_URL;
        // username and password should be set by Secrets Manager or other secure means
        jwtToken = AuthHelper.authenticate("your_username", "your_password");
    }

    @Test
    @DisplayName("Test GET batch listing")
    public void testGetBatches() {
        given()
            .header("Authorization", "Bearer " + jwtToken)
            .when()
            .get(BATCHES_URL)
            .then()
            .statusCode(200);
    }

    @Test
    @DisplayName("Test GET batch listing with query parameters")
    public void testGetBatchListing() {
        given()
            .header("Authorization", "Bearer " + jwtToken)
            .queryParam("status", "Created")
            .queryParam("is_approved", false)
            .when()
            .get(BATCHES_URL)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("batches", not(empty()))
            .body("batches.name", everyItem(notNullValue()))
            .body("batches.status", everyItem(equalTo("Created")))
            .body("batches.telemetry_data", everyItem(notNullValue()))
            .body("batches.quality", everyItem(equalTo("Pending")))
            .body("batches.is_approved", everyItem(equalTo(false)))
            .body("batches.is_shared", everyItem(equalTo(false)))
            .body("batches.id", everyItem(notNullValue()))
            .body("batches.created_at", everyItem(notNullValue()))
            .body("batches.updated_at", everyItem(notNullValue()))
            .body("batches.uploaded_by", everyItem(notNullValue()))
            .body("batches.sample_count", everyItem(notNullValue()))
            .body("batches.uploader", everyItem(notNullValue()))
            .body("batches.portal_upload_complete", everyItem(equalTo(false)))
            .body("total_count", notNullValue());

        // Could check response against a JSON sample file or POJO
    }
}
