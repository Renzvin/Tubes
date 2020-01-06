package com.tubespbp.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BorrowClient {
    public static final String BASE_URL = "http://atma-library.xbanana.id/api/";
    public static Retrofit retrofit = null;
    public static Retrofit getClient(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
