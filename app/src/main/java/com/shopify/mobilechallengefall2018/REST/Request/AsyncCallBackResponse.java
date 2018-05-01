package com.shopify.mobilechallengefall2018.REST.Request;

import android.support.annotation.NonNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public interface AsyncCallBackResponse {
    void onSuccess(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response);
    void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t);
}
