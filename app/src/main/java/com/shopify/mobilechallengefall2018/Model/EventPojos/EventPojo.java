package com.shopify.mobilechallengefall2018.Model.EventPojos;

public class EventPojo {
    public static final String ORDERS_BY_PROVINCE = "ORDERS_BY_PROVINCE";
    public static final String ORDERS_BY_YEAR = "ORDERS_BY_YEAR";
    private final String id;

    public EventPojo(final String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
