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

public class test_IntegrationFlow3_Create_Update_Delete extends BaseTest {

    //-------Test Integration Scenario #3-----------
    // 1. Create Booking --> booking id
    // 2. Create Token
    // 3. Update the Booking (bookingid, token)
    // 4. Delete Booking (bookingid, token)

    @Test(groups = "qa", priority = 1)
    @Owner("Trupti")
    @Description("TC#Int3 - Step 1: Verify the Booking can be created")
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
    @Description("TC#Int3 - Step 2: Verify Updated Booking by ID")
    public void test_UpdateBooking(ITestContext iTestContext){
        System.out.println("----------Step 2: Verify Updated Booking by ID-------");
        System.out.println(iTestContext.getAttribute("bookingid"));
        //Need Booking ID and Token
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken(); //from BaseTest
        iTestContext.setAttribute("token",token);

        //Create PUT & PATCH URL
        String basePathPUTPATCH = APIContrants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathPUTPATCH);

        requestSpecification.basePath(basePathPUTPATCH);
        response = RestAssured.given(requestSpecification)
                .cookie("token",token)
                .when().body(payloadManager.fullUpdatePayloadAsString()).put();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        //deserialize - Reverse to string
        Booking booking = payloadManager.getResponseFromJSON(response.asString());
        assertThat(booking.getFirstname()).isNotNull().isNotBlank();
        assertThat(booking.getFirstname()).isEqualTo("Amey");
        assertThat(booking.getLastname()).isEqualTo("Cate");
    }

    @Test(groups = "qa", priority = 3)
    @Owner("Trupti")
    @Description("TC#Int3 - Step 3: Delete the Booking by ID")
    public void test_DeleteBooking(ITestContext iTestContext){
        System.out.println("----------Step 3: Delete the Booking by ID-------");
        System.out.println(iTestContext.getAttribute("bookingid"));
        System.out.println(iTestContext.getAttribute("token"));
        String token = (String) iTestContext.getAttribute("token");
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        //Create DELETE URL
        String basePathDELETE = APIContrants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathDELETE);

        requestSpecification.basePath(basePathDELETE).cookie("token",token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();
        validatableResponse.statusCode(201);

    }
}

