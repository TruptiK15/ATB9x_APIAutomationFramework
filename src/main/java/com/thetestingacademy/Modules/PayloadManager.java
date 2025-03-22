package com.thetestingacademy.Modules;

import com.google.gson.Gson;
import com.thetestingacademy.Pojos.Booking;
import com.thetestingacademy.Pojos.BookingResponse;
import com.thetestingacademy.Pojos.Bookingdates;

public class PayloadManager {

    //Using GSON
    //Converting the Java Object to String
    Gson gson;
    public String createPayloadBookingAsString(){

        //Payload creation
        Booking booking = new Booking();
        booking.setFirstname("Karan");
        booking.setLastname("Mang");
        booking.setTotalprice(150);
        booking.setDepositpaid(true);

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2025-04-10");
        bookingdates.setCheckout("2025-04-15");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Lunch");
        System.out.println(booking);

        gson = new Gson();
        String jsonStringPayload = gson.toJson(booking);
        System.out.println(jsonStringPayload);
        return jsonStringPayload;
    }

    //Converting the String to Java Objects
    //Deserialization
    public BookingResponse bookingResponseJava(String responseString){
        gson = new Gson();
        BookingResponse bookingResponse = gson.fromJson(responseString, BookingResponse.class);
        return bookingResponse;
    }
}
