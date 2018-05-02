package com.shopify.mobilechallengefall2018.Model.EventPojos;

/**
 * Wrapper DO class for the orders to be filled event
 */
public class NumberOfOrdersCategoryDO extends EventPojo {
    private final String amount;
    public NumberOfOrdersCategoryDO(String id, String amount){
        super(id);
        this.amount = amount;
    }
    public String getAmount() {
        return amount;
    }
}
