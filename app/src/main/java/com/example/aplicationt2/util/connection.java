package com.example.aplicationt2.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class connection {

    public static final String URL = "https://jsonplaceholder.typicode.com/comments/";
    public static Retrofit retrofit = null;

    public static Retrofit getConnecion(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
