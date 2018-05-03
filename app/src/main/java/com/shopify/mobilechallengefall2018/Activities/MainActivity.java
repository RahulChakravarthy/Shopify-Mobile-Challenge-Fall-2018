package com.shopify.mobilechallengefall2018.Activities;

import android.os.Bundle;

import com.shopify.mobilechallengefall2018.Controllers.MainController;
import com.shopify.mobilechallengefall2018.Model.EventPojos.EventPojo;
import com.shopify.mobilechallengefall2018.Model.EventPojos.NumberOfOrdersCategoryDO;
import com.shopify.mobilechallengefall2018.Model.EventPojos.NumberOfOrdersCategoryDOList;
import com.shopify.mobilechallengefall2018.Model.EventPojos.OrderDOList;
import com.shopify.mobilechallengefall2018.R;
import com.shopify.mobilechallengefall2018.Views.MainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Collections;

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
        this.mainView = new MainView(findViewById(R.id.mainLayout), getApplicationContext());
        this.mainView.setupView();
    }


    public void getOrders() {
        this.mainController.getOrdersRequest();
    }

    //All the subscribed methods that create and populate the list view when request is finished
    @Subscribe
    public void onNumberOfOrdersByProvinceEvent(NumberOfOrdersCategoryDOList numberOfOrdersCategoryDOList){
        switch (numberOfOrdersCategoryDOList.getId()){
            case EventPojo.ORDERS_BY_PROVINCE:
                this.mainView.hideLoadingProvincesText(); //hide the loading text
                Collections.sort(numberOfOrdersCategoryDOList.getNumberOfOrdersCategoryDOList());
                numberOfOrdersCategoryDOList.getNumberOfOrdersCategoryDOList().forEach(numberOfOrdersCategoryDO -> {
                    this.mainView.setupOnOrdersByProvinceNumber(numberOfOrdersCategoryDO); //display all the orders by province number
                });
                break;
        }
    }

    @Subscribe
    public void onNumberOfOrdersByYearEvent(NumberOfOrdersCategoryDO numberOfOrdersCategoryDO){
        switch (numberOfOrdersCategoryDO.getId()){
            case EventPojo.ORDERS_BY_YEAR:
                this.mainView.setupOnOrdersByYearNumber(numberOfOrdersCategoryDO);
                break;
        }
    }

    @Subscribe
    public void onOrdersFor2017ListViewEvent(OrderDOList orderDOList){
        switch (orderDOList.getId()){
            case EventPojo.ORDERS_BY_YEAR:
                this.mainView.setupOrdersByYearRecyclerView(orderDOList);
                break;
        }
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
