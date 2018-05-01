package com.shopify.mobilechallengefall2018.Model.EventPojos;

public class EventPojo {
    public static final String ORDERS_TO_FULFILL = "ORDERS_TO_FULFILL";
    public static final String PENDING_PAYMENTS = "PENDING_PAYMENTS";
    public static final String CANCELLED_ORDERS = "CANCELLED_ORDERS";
    private final String id;

    public EventPojo(final String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
