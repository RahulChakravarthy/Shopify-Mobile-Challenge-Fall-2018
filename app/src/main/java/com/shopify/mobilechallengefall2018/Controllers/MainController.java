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
import com.shopify.mobilechallengefall2018.Model.EventPojos.NumberOfOrdersCategoryDOList;
import com.shopify.mobilechallengefall2018.Model.EventPojos.OrderDO;
import com.shopify.mobilechallengefall2018.Model.EventPojos.OrderDOList;
import com.shopify.mobilechallengefall2018.R;
import com.shopify.mobilechallengefall2018.REST.Request.AsyncCallBackResponse;
import com.shopify.mobilechallengefall2018.REST.Request.OrdersRequest;

import org.greenrobot.eventbus.EventBus;
import org.joda.time.DateTime;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class MainController extends BaseController {

    private Context context;
    private OrdersRequest ordersRequest;


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
                    getOrdersByProvince(ordersJson);
                    getOrdersByYear(ordersJson);
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

    //Persist all the orderToBeFulFulled to main memory to be queried for when displaying to display activity
    private static OrderDOList orderListDO;
    public void getOrdersByProvince(JsonObject ordersJson){
        JsonArray ordersListJSONArray = ordersJson.getAsJsonArray("orders");
        List<OrderDO> orderDOList = new ArrayList<>();
        List<NumberOfOrdersCategoryDO> numberOfOrdersCategoryDOList = new ArrayList<>();


        Map<String, Integer> numberOfOrdersByProvince = new HashMap<>();
        //Orders to be fulfilled means that there are some keys in the json object that have null values
        for (JsonElement orderElement : ordersListJSONArray) {
            //Quick check if this order even contains info on province
            if (!orderElement.getAsJsonObject().has("shipping_address")){
                continue;
            }
            //get province where this order originates
            String province =  orderElement.getAsJsonObject().get("shipping_address").getAsJsonObject().get("province").getAsString();
            //if the province already is stored then increment the order counter for that province
            if (numberOfOrdersByProvince.containsKey(province)){
                numberOfOrdersByProvince.put(province, numberOfOrdersByProvince.get(province) + 1);
            } else {
                numberOfOrdersByProvince.put(province, 1);
            }

            //Add order to full order list
            orderDOList.add(new OrderDO(
                    EventPojo.ORDERS_BY_PROVINCE,
                    province,
                    orderElement.getAsJsonObject().get("id").getAsString(),
                    orderElement.getAsJsonObject().get("email").getAsString(),
                    orderElement.getAsJsonObject().get("total_price").getAsString()));
        }

        numberOfOrdersByProvince.forEach((province, value) -> {
            numberOfOrdersCategoryDOList.add(new NumberOfOrdersCategoryDO(EventPojo.ORDERS_BY_PROVINCE, String.valueOf(value), province));
        });

        //Post number of orders to be filled on event bus
        EventBus.getDefault().post(new NumberOfOrdersCategoryDOList(EventPojo.ORDERS_BY_PROVINCE, numberOfOrdersCategoryDOList));

        //Store all the orderDoLists to memory but only display first 10 orders that fulfill this category to the event bus
        orderListDO = new OrderDOList(EventPojo.ORDERS_BY_PROVINCE, orderDOList);
    }

    public static OrderDOList getOrderListDO(){
        return orderListDO;
    }

    public void getOrdersByYear(JsonObject ordersJson){
        JsonArray ordersListJSONArray = ordersJson.getAsJsonArray("orders");
        int ordersFromYear2017 = 0;
        List<OrderDO> orderDOList = new ArrayList<>();

        for (JsonElement orderElement : ordersListJSONArray) {
            //Check if the date of creation for this order was in 2017
            if ((new DateTime(orderElement.getAsJsonObject().get("created_at").getAsString())).getYear() == 2017) {
                //Increment counter
                ordersFromYear2017++;
                //Add this object to list
                orderDOList.add(new OrderDO(
                        EventPojo.ORDERS_BY_PROVINCE,
                        orderElement.getAsJsonObject().has("shipping_address")? orderElement.getAsJsonObject().get("shipping_address").getAsJsonObject().get("province").getAsString() : null,
                        orderElement.getAsJsonObject().get("id").getAsString(),
                        orderElement.getAsJsonObject().get("email").getAsString(),
                        orderElement.getAsJsonObject().get("total_price").getAsString()));
                }
        }

        //Post number of pending payments orders to event bus
        EventBus.getDefault().post(new NumberOfOrdersCategoryDO(EventPojo.ORDERS_BY_YEAR, String.valueOf(ordersFromYear2017), null));

        //Post first 10 orders that fulfill this category to the event bus
        EventBus.getDefault().post(new OrderDOList(EventPojo.ORDERS_BY_YEAR, orderDOList.size() >= 10 ? orderDOList.subList(0, 10) : orderDOList));
    }

}
