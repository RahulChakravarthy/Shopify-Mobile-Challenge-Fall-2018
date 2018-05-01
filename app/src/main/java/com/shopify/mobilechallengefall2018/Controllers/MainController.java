package com.shopify.mobilechallengefall2018.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shopify.mobilechallengefall2018.R;
import com.shopify.mobilechallengefall2018.REST.Request.AsyncCallBackResponse;
import com.shopify.mobilechallengefall2018.REST.Request.OrdersRequest;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class MainController extends BaseController {

    private Context context;
    private OrdersRequest ordersRequest;

    //We want to process these tasks in parallel
    ExecutorService executorService = Executors.newFixedThreadPool(3);


    public MainController(Context context){
        this.context = context;
        this.ordersRequest = new OrdersRequest();
    }

    /**
     * Controller method for fetching the data from the back end api
     */
    public void getOrdersRequest(){
        this.ordersRequest.getOrders(new AsyncCallBackResponse() {
            @Override
            public void onSuccess(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                //On response, parse and format data then push to event bus to display on view
                //Convert it to json object so we can operate on it easier
                try {
                    JsonObject ordersJson = (JsonObject) (new JsonParser()).parse(new InputStreamReader(response.body().byteStream(), "UTF-8"));
                    executorService.submit(()->{
                        getOrderToBeFilled(ordersJson);
                    });

                    executorService.submit(()->{
                        getPendingPayments(ordersJson);
                    });

                    executorService.submit(()->{
                        getCancelledOrders(ordersJson);
                    });
                } catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(context, R.string.cannot_find_servers, Toast.LENGTH_LONG).show();
            }
        });
    }



    public class OrdersToBeFilled {
        public final Integer ordersToBeFilled;
        public OrdersToBeFilled(Integer ordersToBeFilled){
            this.ordersToBeFilled = ordersToBeFilled;
        }
        public Integer getOrdersToBeFilled() {
            return ordersToBeFilled;
        }
    }
    public void getOrderToBeFilled(JsonObject ordersJson){
        int ordersToBeFilled = 0;
        JsonArray ordersList = ordersJson.getAsJsonArray("orders");


        //Post onto event bus
        EventBus.getDefault().post(new OrdersToBeFilled(ordersToBeFilled));
    }

    public class PendingPayments {

    }
    public void getPendingPayments(JsonObject ordersJson){

    }

    public class CancelledOrders {

    }
    public void getCancelledOrders(JsonObject ordersJson){

    }
}
