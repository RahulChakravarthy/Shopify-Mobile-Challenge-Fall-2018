package com.shopify.mobilechallengefall2018.Model.EventPojos;

import android.support.annotation.NonNull;

/**
 * Main data object for the orders
 */
public class OrderDO extends EventPojo implements Comparable<OrderDO>{

    //Fields we want to store
    private final String province;
    private final String orderId;
    private final String email;
    private final String totalPrice;
    private final String date;

    public OrderDO(String id, String province, String orderId, String email, String totalPrice, String date) {
        super(id);
        this.province = province;
        this.orderId = orderId;
        this.email = email;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    @Override
    public int compareTo(@NonNull OrderDO orderDO) {
        return this.province.compareTo(orderDO.province);
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

    public String getProvince(){
        return this.province;
    }

    public String getDate() {
        return date;
    }
}
