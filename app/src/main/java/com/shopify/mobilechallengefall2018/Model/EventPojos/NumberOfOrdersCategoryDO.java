package com.shopify.mobilechallengefall2018.Model.EventPojos;

import android.support.annotation.NonNull;

/**
 * Wrapper DO class for the orders to be filled event
 */
public class NumberOfOrdersCategoryDO extends EventPojo implements Comparable<NumberOfOrdersCategoryDO> {
    private final String province;
    private final String amount;
    public NumberOfOrdersCategoryDO(String id, String amount, String province){
        super(id);
        this.amount = amount;
        this.province = province;
    }
    public String getAmount() {
        return amount;
    }

    public String getProvince() {
        return province;
    }

    @Override
    public int compareTo(@NonNull NumberOfOrdersCategoryDO numberOfOrdersCategoryDO) {
        return this.province.compareTo(numberOfOrdersCategoryDO.province);
    }
}
