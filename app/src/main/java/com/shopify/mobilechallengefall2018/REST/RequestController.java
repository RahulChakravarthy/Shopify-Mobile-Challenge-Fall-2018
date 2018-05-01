package com.shopify.mobilechallengefall2018.REST;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestController {

    /**
     * Main request handler object
     */
    private Retrofit retrofit = RetrofitClient.buildRetrofitClient(HOSTNAME, GsonConverterFactory.create());

    //Hard code in the rest api end point
    public static String HOSTNAME = "https://shopicruit.myshopify.com/admin/";

    private static final RequestController mInstance = new RequestController();

    public static RequestController getInstance() {
        return mInstance;
    }

    private RequestController() {
    }

    /**
     * USE THIS IN THE FUTURE WHEN WE NEED TO FETCH THE BACKEND API HOSTNAME
     * This method is to future proof. We want to make a rest call to fetch the host name and on response we reset the retrofit client
     * @param hostName : name of new backend host name
     * @return
     */
    public Retrofit setBackEndAPIRestHostName(String hostName){
        //By default, Retrofit can only deserialize HTTP bodies in okHttp's reponse body type and can only
        //accept Request body type for body, so we attach Gson converter factory so we can deserialize the returned JSON data
        this.retrofit = RetrofitClient.buildRetrofitClient(hostName, GsonConverterFactory.create());
        return this.retrofit; //return it for method chaining
    }

    public Retrofit getRetrofit(){
        return this.retrofit;
    }
}
