package com.shopify.mobilechallengefall2018.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shopify.mobilechallengefall2018.Model.EventPojos.EventPojo;
import com.shopify.mobilechallengefall2018.Model.EventPojos.NumberOfOrdersCategoryDO;
import com.shopify.mobilechallengefall2018.Model.EventPojos.OrderDO;
import com.shopify.mobilechallengefall2018.Model.EventPojos.OrderDOList;
import com.shopify.mobilechallengefall2018.R;
import com.shopify.mobilechallengefall2018.REST.Request.AsyncCallBackResponse;
import com.shopify.mobilechallengefall2018.REST.Request.OrdersRequest;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
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


    public void getOrderToBeFilled(JsonObject ordersJson){
        JsonArray ordersListJSONArray = ordersJson.getAsJsonArray("orders");
        int ordersToBeFilled = 0;
        List<OrderDO> orderDOList = new ArrayList<>();

        //Orders to be fulfilled means that there are some keys in the json object that have null values
        for (JsonElement orderElement : ordersListJSONArray) {
            if (orderElement.getAsJsonObject().get("fulfillment_status").isJsonNull()) {
                //Increment counter
                ordersToBeFilled++;
                //Add this object to list
                orderDOList.add(new OrderDO(
                        EventPojo.ORDERS_TO_FULFILL,
                        orderElement.getAsJsonObject().get("id").getAsString(),
                        orderElement.getAsJsonObject().get("email").getAsString(),
                        orderElement.getAsJsonObject().get("total_price").getAsString()));
                }
            }

        //Post number of orders to be filled on event bus
        EventBus.getDefault().post(new NumberOfOrdersCategoryDO(EventPojo.ORDERS_TO_FULFILL, ordersToBeFilled));

        //Post the first 10 orders that fulfill this category to the event bus
        EventBus.getDefault().post(new OrderDOList(EventPojo.ORDERS_TO_FULFILL, orderDOList.size() >= 10 ?  orderDOList.subList(0, 10) : orderDOList));
    }

    public void getPendingPayments(JsonObject ordersJson){
        JsonArray ordersListJSONArray = ordersJson.getAsJsonArray("orders");
        int pendingPaymentOrders = 0;
        List<OrderDO> orderDOList = new ArrayList<>();

        for (JsonElement orderElement : ordersListJSONArray) {
            if (orderElement.getAsJsonObject().get("").isJsonNull()) {
                //Increment counter
                pendingPaymentOrders++;
                //Add this object to list
                orderDOList.add(new OrderDO(
                        EventPojo.ORDERS_TO_FULFILL,
                        orderElement.getAsJsonObject().get("id").getAsString(),
                        orderElement.getAsJsonObject().get("email").getAsString(),
                        orderElement.getAsJsonObject().get("total_price").getAsString()));
                }
        }

        //Post number of pending payments orders to event bus
        EventBus.getDefault().post(new NumberOfOrdersCategoryDO(EventPojo.PENDING_PAYMENTS, pendingPaymentOrders));

        //Post first 10 orders that fulfill this category to the event bus
        EventBus.getDefault().post(orderDOList.size() >= 10 ? orderDOList.subList(0, 10) : orderDOList);
    }

    public void getCancelledOrders(JsonObject ordersJson){
        JsonArray ordersListJSONArray = ordersJson.getAsJsonArray("orders");
        int cancelledOrders = 0;
        List<OrderDO> orderDOList = new ArrayList<>();

        for (JsonElement orderElement : ordersListJSONArray) {
            if (!orderElement.getAsJsonObject().get("cancelled_at").isJsonNull()){
                //this means this order has been cancelled
                //Increment counter
                cancelledOrders++;
                //Add this object to list
                orderDOList.add(new OrderDO(
                        EventPojo.ORDERS_TO_FULFILL,
                        orderElement.getAsJsonObject().get("id").getAsString(),
                        orderElement.getAsJsonObject().get("email").getAsString(),
                        orderElement.getAsJsonObject().get("total_price").getAsString()));
            }
        }

        //Post number of cancelled payments to event bus
        EventBus.getDefault().post(new NumberOfOrdersCategoryDO(EventPojo.CANCELLED_ORDERS, cancelledOrders));

        //Post first 10 orders that fulfill this category to the event bus
        EventBus.getDefault().post(orderDOList.size() >= 10 ? orderDOList.subList(0, 10) : orderDOList);
    }

}
