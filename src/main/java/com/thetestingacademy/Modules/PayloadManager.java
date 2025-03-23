package com.thetestingacademy.Modules;

import com.google.gson.Gson;
import com.thetestingacademy.Pojos.*;

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
    //Booking Response i.e. with ID (POST method response)
    public BookingResponse bookingResponseJava(String responseString){
        gson = new Gson();
        BookingResponse bookingResponse = gson.fromJson(responseString, BookingResponse.class);
        return bookingResponse;
    }

    //Only Booking (GET method response)
     public Booking getResponseFromJSON(String getresponse) {
         gson = new Gson();
         Booking booking = gson.fromJson(getresponse, Booking.class);
         return booking;
     }

     //Token auth Payload
    //Java to Json
    public String setAuthPayload(){
        Auth auth = new Auth();
        auth.setUsername("admin");
        auth.setPassword("password123");
        //convert to string
        Gson gson = new Gson();
        String jsonPayloadString = gson.toJson(auth);
        System.out.println("Payload set to be : "+jsonPayloadString);
        return jsonPayloadString;
        //we get token as response so create deserialization
    }

    //Json to Java
    public String getTokenFromJSON(String tokenResponse){
        gson = new Gson();
        TokenResponse tokenResponse1 = gson.fromJson(tokenResponse, TokenResponse.class);
        return tokenResponse1.getToken();
    }

    //Update the Payload for PUT & PATACH
    public String fullUpdatePayloadAsString(){
        Booking booking = new Booking();
        booking.setFirstname("Amey");
        booking.setLastname("Cate");
        booking.setTotalprice(100);
        booking.setDepositpaid(true);

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2025-04-20");
        bookingdates.setCheckout("2025-04-25");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Dinner");
        return gson.toJson(booking);
    }
}
