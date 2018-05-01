package com.shopify.mobilechallengefall2018.Activities;

import android.os.Bundle;

import com.shopify.mobilechallengefall2018.Controllers.MainController;
import com.shopify.mobilechallengefall2018.R;
import com.shopify.mobilechallengefall2018.Views.MainView;

public class MainActivity extends BaseActivity {

    private MainController mainController;
    private MainView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopify_challenge_fall2018);

        initializeController();
        initializeView();

        //Make REST API call as soon as app loads
        getOrders();
    }

    private void initializeController() {
        this.mainController = new MainController(getApplicationContext());
    }

    private void initializeView() {
        this.mainView = new MainView();
    }


    public void getOrders() {
        this.mainController.getOrdersRequest();
    }
}
