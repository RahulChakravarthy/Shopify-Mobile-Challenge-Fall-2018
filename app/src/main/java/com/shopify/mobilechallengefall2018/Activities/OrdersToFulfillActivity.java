package com.shopify.mobilechallengefall2018.Activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shopify.mobilechallengefall2018.Controllers.MainController;
import com.shopify.mobilechallengefall2018.Model.EventPojos.OrderDOList;
import com.shopify.mobilechallengefall2018.R;
import com.shopify.mobilechallengefall2018.Views.Adapters.OrdersAdapter;

public class OrdersToFulfillActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_to_fulfill);

        setupFulfilledOrderRecyclerView();
    }

    //Since this activity is just for displaying the recyclerview with all the fulfilled orders we will skip the usual architecture for simplicity
    public void setupFulfilledOrderRecyclerView(){
        OrderDOList orderDOList = MainController.getOrderListDO();
        OrdersAdapter ordersAdapter = new OrdersAdapter(orderDOList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView ordersToFulFillRecyclerView = findViewById(R.id.fulfilledOrdersActivity).findViewById(R.id.allNeedToFufillOrdersRecyclerView);
        ordersToFulFillRecyclerView.setLayoutManager(layoutManager);
        ordersToFulFillRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ordersToFulFillRecyclerView.setAdapter(ordersAdapter);
    }
}
