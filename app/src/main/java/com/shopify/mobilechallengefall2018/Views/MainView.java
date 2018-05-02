package com.shopify.mobilechallengefall2018.Views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shopify.mobilechallengefall2018.Activities.OrdersToFulfillActivity;
import com.shopify.mobilechallengefall2018.Model.EventPojos.NumberOfOrdersCategoryDO;
import com.shopify.mobilechallengefall2018.Model.EventPojos.OrderDOList;
import com.shopify.mobilechallengefall2018.R;
import com.shopify.mobilechallengefall2018.Views.Adapters.OrdersAdapter;

public class MainView extends BaseView {

    public MainView(ViewGroup viewGroup, Context context){
        this.viewGroup = viewGroup;
        this.context = context;
    }

    @Override
    public void setupView() {
        //do nothing
        onFulfilledOrdersHeaderClicked();
    }


    public void onFulfilledOrdersHeaderClicked(){
        this.viewGroup.findViewById(R.id.numberOfOrdersToBeFulfilledText).setOnClickListener(view -> {
            context.startActivity(new Intent(context, OrdersToFulfillActivity.class));
        });
    }

    public void setupOnOrdersFulfilledNumber(NumberOfOrdersCategoryDO categoryDO){
        ((TextView)this.viewGroup.findViewById(R.id.ordersToBeFulfilledText)).setText(categoryDO.getAmount());
    }

    public void setupOnPendingPaymentsOrderNumber(NumberOfOrdersCategoryDO categoryDO){
        ((TextView)this.viewGroup.findViewById(R.id.pendingPaymentOrders)).setText(categoryDO.getAmount());
    }

    public void setupOnCancelledOrdersNumber(NumberOfOrdersCategoryDO categoryDO){
        ((TextView)this.viewGroup.findViewById(R.id.cancelledOrders)).setText(categoryDO.getAmount());
    }

    public void setupOrdersToFulFillRecyclerView(OrderDOList orderDOList){
        OrdersAdapter ordersAdapter = new OrdersAdapter(orderDOList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        RecyclerView ordersToFulFillRecyclerView = this.viewGroup.findViewById(R.id.ordersToFulfilRecyclerView);
        ordersToFulFillRecyclerView.setLayoutManager(layoutManager);
        ordersToFulFillRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ordersToFulFillRecyclerView.setAdapter(ordersAdapter);
    }

    public void setupPendingPaymentOrdersRecyclerView(OrderDOList orderDOList){
        OrdersAdapter ordersAdapter = new OrdersAdapter(orderDOList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        RecyclerView ordersToFulFillRecyclerView = this.viewGroup.findViewById(R.id.pendingPaymentOrdersRecyclerView);
        ordersToFulFillRecyclerView.setLayoutManager(layoutManager);
        ordersToFulFillRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ordersToFulFillRecyclerView.setAdapter(ordersAdapter);
    }

    public void setupCancelledPaymentOrdersRecyclerView(OrderDOList orderDOList){
        OrdersAdapter ordersAdapter = new OrdersAdapter(orderDOList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        RecyclerView ordersToFulFillRecyclerView = this.viewGroup.findViewById(R.id.cancelledPaymentsRecyclerView);
        ordersToFulFillRecyclerView.setLayoutManager(layoutManager);
        ordersToFulFillRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ordersToFulFillRecyclerView.setAdapter(ordersAdapter);
    }

}
