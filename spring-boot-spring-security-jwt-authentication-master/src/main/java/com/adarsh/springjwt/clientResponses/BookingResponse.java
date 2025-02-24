package com.adarsh.springjwt.clientResponses;

import lombok.Data;

import java.util.List;

@Data
public class BookingResponse {
    private int id;
    private  String customerId;
    private String orderid;
    private String paymentMethod;
    private int items;
    private List<SareesListBookingResponse> sareesList;
}
