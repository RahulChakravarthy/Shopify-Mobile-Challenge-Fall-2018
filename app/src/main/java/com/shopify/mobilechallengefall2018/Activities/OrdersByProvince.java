package com.shopify.mobilechallengefall2018.Activities;

import android.os.Bundle;

import com.shopify.mobilechallengefall2018.R;
import com.shopify.mobilechallengefall2018.Views.OrdersByProvinceView;

public class OrdersByProvince extends BaseActivity {

    private OrdersByProvinceView ordersByProvinceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_by_province);

        setupView();
    }

    private void setupView() {
        this.ordersByProvinceView = new OrdersByProvinceView(findViewById(R.id.allOrdersByProvinceTopLayout), getApplicationContext());
        this.ordersByProvinceView.setupView();
    }

}
