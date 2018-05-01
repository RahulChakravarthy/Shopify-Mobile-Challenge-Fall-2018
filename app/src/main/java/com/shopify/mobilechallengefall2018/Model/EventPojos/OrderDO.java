package com.shopify.mobilechallengefall2018.Model.EventPojos;

/**
 * Main data object for the orders
 */
public class OrderDO extends EventPojo {

    //Fields we want to store
    private String orderId;
    private String email;
    private String totalPrice;

    public OrderDO(String id, String orderId, String email, String totalPrice) {
        super(id);
        this.orderId = orderId;
        this.email = email;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getEmail() {
        return email;
    }

    public String getTotalPrice() {
        return totalPrice;
    }
}
