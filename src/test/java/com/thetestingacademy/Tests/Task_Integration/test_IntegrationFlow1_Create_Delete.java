package com.thetestingacademy.Tests.Task_Integration;

import com.thetestingacademy.Base.BaseTest;
import com.thetestingacademy.EndPoints.APIContrants;
import com.thetestingacademy.Pojos.Booking;
import com.thetestingacademy.Pojos.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class test_IntegrationFlow1_Create_Delete extends BaseTest {

    //-------Test Integration Scenario #1-----------
    // 1. Create Booking --> booking id
    // 2. Create Token
    // 3. Delete Booking (bookingid, token)

    @Test(groups = "qa", priority = 1)
    @Owner("Trupti")
    @Description("TC#Int1 - Step 1: Verify the Booking can be created")
    public void test_CreateBooking(ITestContext iTestContext){

        System.out.println("----------Step 1: Verify the Booking can be created-------");
        requestSpecification.basePath(APIContrants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification)
                        .when().body(payloadManager.createPayloadBookingAsString()).post();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"Karan");
        System.out.println(bookingResponse.getBookingid());

        //For passing value of Booking id to another Test case
        iTestContext.setAttribute("bookingid",bookingResponse.getBookingid());
    }

    @Test(groups = "qa", priority = 2)
    @Owner("Trupti")
    @Description("TC#Int1 - Step 2: Delete the Booking by ID")
    public void test_DeleteBooking(ITestContext iTestContext){
        System.out.println("----------Step 2: Delete the Booking by ID-------");
        System.out.println(iTestContext.getAttribute("bookingid"));
        System.out.println(iTestContext.getAttribute("token"));
        String token = getToken(); //from BaseTest
        iTestContext.setAttribute("token",token);
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

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
    @Description("TC#Int1 - Step 3: Verify the Booking ID")
    public void test_VerifyBooking(ITestContext iTestContext){
        //ITestContext - it is a Listners
        System.out.println("----------Step 3: Verify the Booking ID-------");
        System.out.println("New Book ID: " + iTestContext.getAttribute("bookingid"));
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        //GET Request - to verify firstname
        String basePathGET = APIContrants.CREATE_UPDATE_BOOKING_URL+"/"+bookingid;
        System.out.println("GET PATH: " + basePathGET);    //url for GET

        requestSpecification.basePath(basePathGET);
        response = RestAssured
                .given(requestSpecification)
                .when().get();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);
    }
}

