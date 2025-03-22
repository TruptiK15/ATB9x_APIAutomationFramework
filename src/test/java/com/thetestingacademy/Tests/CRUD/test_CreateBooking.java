package com.thetestingacademy.Tests.CRUD;

import com.thetestingacademy.Base.BaseTest;
import com.thetestingacademy.EndPoints.APIContrants;
import com.thetestingacademy.Modules.PayloadManager;
import com.thetestingacademy.Pojos.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class test_CreateBooking extends BaseTest {

    //Individual TC
    //Add Positive and negative TC

    @Owner("Trupti")
    @Description("Verify the POST request is working fine")
    @Test(groups = "qa")
    public void test_VerifyCreateBookingPOST01(){
        requestSpecification.basePath(APIContrants.CREATE_UPDATE_BOOKING_URL);

        response = RestAssured.given(requestSpecification)
                .when().body(payloadManager.createPayloadBookingAsString()).post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());

        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"Karan");
    }
}
