package com.thetestingacademy.Tests.Task_Integration;

import com.thetestingacademy.Base.BaseTest;
import com.thetestingacademy.EndPoints.APIContrants;
import com.thetestingacademy.Pojos.Booking;
import com.thetestingacademy.Pojos.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class test_IntegrationFlow2_Get_Delete extends BaseTest {

    //-------Test Integration Scenario #2-----------
    // 1. Verify create booking is working - get 1st booking id
    // 2. Delete Booking (bookingid, token)

    @Test(groups = "qa", priority = 1)
    @Owner("Trupti")
    @Description("TC#Int2 - Step 1: Get Booking ID from All Booking IDs")
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
    @Description("TC#Int2 - Step 2: Delete the Booking by ID")
    public void test_DeleteBooking(ITestContext iTestContext){
        System.out.println("----------Step 2: Delete the Booking by ID-------");

        System.out.println(iTestContext.getAttribute("bookingid"));
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        //Create Token
        String token = getToken(); //from BaseTest
        iTestContext.setAttribute("token",token);
        System.out.println(iTestContext.getAttribute("token"));

        //Create DELETE URL - delete 1st booking id
        String basePathDELETE = APIContrants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathDELETE);

        requestSpecification.basePath(basePathDELETE).cookie("token",token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();
        validatableResponse.statusCode(201);
    }
}

