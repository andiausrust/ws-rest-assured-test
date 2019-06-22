package com.andi.app.webservice.wsrestassuredtest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertNotNull;

public class TestCreateUser {

    private final String CONTEXT_PATH = "/webservices";

    @BeforeEach
    void setUp() throws Exception {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8888;
    }

    @Test
    public void testCreateUser(){

        List<Map<String, Object>> userAddresses = new ArrayList<>();

        Map<String, Object> shippingAddress = new HashMap<>();
        shippingAddress.put("city", "Vancouver");
        shippingAddress.put("country", "Canada");
        shippingAddress.put("streetName", "123 Street");
        shippingAddress.put("postalCode", 12345);
        shippingAddress.put("type", "shipping");

        userAddresses.add(shippingAddress);

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("firstName", "Andi");
        userDetails.put("lastName", "Mayer");
        userDetails.put("email", "tstemail@test.com");
        userDetails.put("password", "112");
        userDetails.put("addresses", userAddresses);

        Response response = given()
                .contentType("application/json")
                .accept("application/json")
                .body(userDetails)
                .when()
                .post(CONTEXT_PATH + "/users")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .response();

        String userId = response.jsonPath().get("userId");
        assertNotNull(userId);
    }
}
