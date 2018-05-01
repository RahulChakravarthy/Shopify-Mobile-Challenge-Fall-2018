package com.shopify.mobilechallengefall2018.REST;


import retrofit2.Retrofit;
import retrofit2.Converter.Factory;

/**
 * RetrofitClient wrapper class
 */
public class RetrofitClient {

    public static Retrofit buildRetrofitClient(String baseUrl, Factory factory){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(factory);
        return builder.build();
    }
}
