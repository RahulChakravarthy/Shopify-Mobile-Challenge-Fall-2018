package com.shopify.mobilechallengefall2018.Model.EventPojos;

import java.util.List;

public class NumberOfOrdersCategoryDOList extends EventPojo {

    private final List<NumberOfOrdersCategoryDO> numberOfOrdersCategoryDOList;

    public NumberOfOrdersCategoryDOList(String id, List<NumberOfOrdersCategoryDO> numberOfOrdersCategoryDOList) {
        super(id);
        this.numberOfOrdersCategoryDOList = numberOfOrdersCategoryDOList;
    }

    public List<NumberOfOrdersCategoryDO> getNumberOfOrdersCategoryDOList() {
        return numberOfOrdersCategoryDOList;
    }
}
