package com.thetestingacademy.Base;

import com.thetestingacademy.Assertions.AssertActions;
import com.thetestingacademy.EndPoints.APIContrants;
import com.thetestingacademy.Modules.PayloadManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BaseTest {

    //Common to All Test Cases
    // Base URL, Content Type - json - common
    public RequestSpecification requestSpecification;
    public AssertActions assertActions;
    public ValidatableResponse validatableResponse;
    public PayloadManager payloadManager;
    public JsonPath jsonPath;
    public Response response;

    @BeforeTest
    public void setUp(){
        //Base URL, Content Type - json
        //Create instances of below classes
        payloadManager = new PayloadManager();
        assertActions = new AssertActions();

        requestSpecification = RestAssured.given()
                .baseUri(APIContrants.BASE_URL)
                .contentType(ContentType.JSON)
                .log().all();

//        requestSpecification = new RequestSpecBuilder()
//                .setBaseUri(APIContrants.BASE_URL)
//                .addHeader("Content-Type","application/json")
//                .build().log().all();
    }

    public String getToken(){
        requestSpecification = RestAssured
                .given()
                .baseUri(APIContrants.BASE_URL)
                .basePath(APIContrants.AUTH_URL);

        //Setting the payload
        String payload = payloadManager.setAuthPayload();

        //Get the Token
        response = requestSpecification.contentType(ContentType.JSON).body(payload).when().post();

        //String Extraction (extract token)
        String token = payloadManager.getTokenFromJSON(response.asString());
        return token;
    }
}
