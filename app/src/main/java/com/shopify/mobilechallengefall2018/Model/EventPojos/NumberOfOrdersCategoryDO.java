package com.shopify.mobilechallengefall2018.Model.EventPojos;

/**
 * Wrapper DO class for the orders to be filled event
 */
public class NumberOfOrdersCategoryDO extends EventPojo {
    public final Integer amount;
    public NumberOfOrdersCategoryDO(String id, Integer amount){
        super(id);
        this.amount = amount;
    }
    public Integer NumberOfOrdersCategoryDO() {
        return amount;
    }
}
