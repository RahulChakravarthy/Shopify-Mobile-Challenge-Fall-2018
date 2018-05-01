package com.shopify.mobilechallengefall2018.Activities;

import android.os.Bundle;

import com.shopify.mobilechallengefall2018.Controllers.MainController;
import com.shopify.mobilechallengefall2018.Model.EventPojos.EventPojo;
import com.shopify.mobilechallengefall2018.Model.EventPojos.NumberOfOrdersCategoryDO;
import com.shopify.mobilechallengefall2018.Model.EventPojos.OrderDOList;
import com.shopify.mobilechallengefall2018.R;
import com.shopify.mobilechallengefall2018.Views.MainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends BaseActivity {

    private MainController mainController;
    private MainView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeController();
        initializeView();

        //Make REST API call as soon as app loads
        getOrders();
    }

    private void initializeController() {
        this.mainController = new MainController(getApplicationContext());
        EventBus.getDefault().register(this);
    }

    private void initializeView() {
        this.mainView = new MainView();
        this.mainView.setupView();
    }


    public void getOrders() {
        this.mainController.getOrdersRequest();
    }

    //All the subscribed methods that create and populate the list view when request is finished
    @Subscribe
    public void onOrdersToFulfillEvent(NumberOfOrdersCategoryDO numberOfOrdersCategoryDO){
        switch (numberOfOrdersCategoryDO.getId()){
            case EventPojo.ORDERS_TO_FULFILL:
                break;
            case EventPojo.PENDING_PAYMENTS:
                break;
            case EventPojo.CANCELLED_ORDERS:
                break;

        }
    }

    @Subscribe
    public void onPendingPaymentsEvent(OrderDOList orderDOList){
        switch (orderDOList.getId()){
            case EventPojo.ORDERS_TO_FULFILL:
                break;
            case EventPojo.PENDING_PAYMENTS:
                break;
            case EventPojo.CANCELLED_ORDERS:
                break;

        }
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
