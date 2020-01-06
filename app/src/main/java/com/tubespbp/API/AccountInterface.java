package com.tubespbp.API;

import com.tubespbp.DAO.accountDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AccountInterface {

    @GET("accounts/show")
    Call<List<accountDAO>> getAccount();

    @POST("accounts/create")
    @FormUrlEncoded
    Call<String> addAccount(@Field("username")String username,
                            @Field("email")String email,
                            @Field("role")String role);

    @PUT("accounts/{email}")
    @FormUrlEncoded
    Call<String> putAccount(@Field("username") String username,
                           @Path("email") String email,
                           @Field("role") String role);

    @POST("accounts/search/{email}")
    Call<List<accountDAO>> getAccount(@Path("email") String email);

    @DELETE("accounts/{username}")
    Call<accountDAO> deleteAccount(@Path("username") String username);
}
