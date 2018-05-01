package com.shopify.mobilechallengefall2018.REST.Request;

import android.support.annotation.NonNull;

import com.shopify.mobilechallengefall2018.REST.RequestController;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Main class handler for making REST API calls to shopify's backend
 */
public class OrdersRequest {

    public interface GetOrders {
        @GET("orders.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6")
        Call<ResponseBody> getOrders();
    }

    public void getOrders(final AsyncCallBackResponse asyncCallBackResponse){
        GetOrders getOrders = RequestController.getInstance().getRetrofit().create(GetOrders.class);
        getOrders.getOrders().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                asyncCallBackResponse.onSuccess(call, response);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                asyncCallBackResponse.onFailure(call, t);
            }
        });

    }
}
