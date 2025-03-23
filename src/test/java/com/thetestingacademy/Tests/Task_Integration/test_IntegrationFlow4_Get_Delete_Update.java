package com.thetestingacademy.Tests.Task_Integration;

import com.thetestingacademy.Base.BaseTest;
import com.thetestingacademy.EndPoints.APIContrants;
import com.thetestingacademy.Pojos.Booking;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class test_IntegrationFlow4_Get_Delete_Update extends BaseTest {

    //-------Test Integration Scenario #4-----------
    // 1. Get the 1st booking id
    // 2. Delete a Booking
    // 3. Try to Update it

    @Test(groups = "qa", priority = 1)
    @Owner("Trupti")
    @Description("TC#Int4 - Step 1: Get Booking ID from All Booking IDs")
    public void test_VerifyBooking(ITestContext iTestContext){
        System.out.println("----------Step 1: Get Booking ID from All Booking IDs-------");

        //get all bookings
        requestSpecification.basePath(APIContrants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured
                .given(requestSpecification).get();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        // Parse first booking ID
        Integer bookingid = response.jsonPath().getInt("[0].bookingid");
        System.out.println("1st Booking ID: "+bookingid);
        iTestContext.setAttribute("bookingid",bookingid);
    }

    @Test(groups = "qa", priority = 2)
    @Owner("Trupti")
    @Description("TC#Int4 - Step 2: Delete the Booking by ID")
    public void test_DeleteBooking(ITestContext iTestContext){
        System.out.println("----------Step 2: Delete the Booking by ID-------");
        System.out.println(iTestContext.getAttribute("bookingid"));

        //Need Booking ID and Token
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken(); //from BaseTest
        iTestContext.setAttribute("token",token);

        //Create DELETE URL
        String basePathDELETE = APIContrants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathDELETE);

        requestSpecification.basePath(basePathDELETE).cookie("token",token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();
        validatableResponse.statusCode(201);

    }
    @Test(groups = "qa", priority = 3)
    @Owner("Trupti")
    @Description("TC#Int4 - Step 3: Try to Update the Booking by ID")
    public void test_UpdateBooking(ITestContext iTestContext){
        System.out.println("----------Step 3: Try to Update the Booking by ID-------");
        System.out.println(iTestContext.getAttribute("bookingid"));

        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        System.out.println(iTestContext.getAttribute("token"));
        String token = (String) iTestContext.getAttribute("token");

        //Create PUT & PATCH URL
        String basePathPUTPATCH = APIContrants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathPUTPATCH);

        requestSpecification.basePath(basePathPUTPATCH);
        response = RestAssured.given(requestSpecification)
                .cookie("token",token)
                .when().body(payloadManager.fullUpdatePayloadAsString()).put();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(405);
    }

}

