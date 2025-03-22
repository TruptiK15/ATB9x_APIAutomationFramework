package com.thetestingacademy.Tests.Sample;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestIntegrationSample {

    // Create a Booking
    // Create a Token
    // Verify - Get Booking
    // Update Booking
    // Delete Booking

    @Test(groups = "qa", priority = 1)
    @Owner("Trupti")
    @Description("TC#Int1 - Step 1: Verify the Booking can be created")
    public void test_CreateBooking(){
        Assert.assertTrue(true);
    }

    @Test(groups = "qa", priority = 2)
    @Owner("Trupti")
    @Description("TC#Int2 - Step 2: Verify the Booking ID")
    public void test_VerifyBooking(){
        Assert.assertTrue(true);
    }

    @Test(groups = "qa", priority = 3)
    @Owner("Trupti")
    @Description("TC#Int3 - Step 3: Verify the Booking is Updated")
    public void test_UpdateBooking(){
        Assert.assertTrue(true);
    }

    @Test(groups = "qa", priority = 4)
    @Owner("Trupti")
    @Description("TC#Int4 - Step 4: Verify the Booking is Deleted")
    public void test_DeleteBooking(){
        Assert.assertTrue(true);
    }
}
