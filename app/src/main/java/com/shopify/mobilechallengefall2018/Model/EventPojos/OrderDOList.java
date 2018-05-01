package com.shopify.mobilechallengefall2018.Model.EventPojos;

import java.util.List;

public class OrderDOList extends EventPojo {

    private final List<OrderDO> orderDOList;

    public OrderDOList(String id, List<OrderDO> orderDOList) {
        super(id);
        this.orderDOList = orderDOList;
    }

    public List<OrderDO> getOrderDOList() {
        return orderDOList;
    }
}
