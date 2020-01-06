package com.tubespbp.API;

import com.tubespbp.DAO.accountDAO;
import com.tubespbp.DAO.bukuDAO;
import com.tubespbp.DAO.requestDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequestInterface {
    @GET("request/show")
    Call<List<requestDAO>> getRequest();

    @POST("request/create")
    @FormUrlEncoded
    Call<String> addRequest(@Field("judul")String judul,
                         @Field("pengarang")String pengarang,
                         @Field("penerbit")String penerbit,
                         @Field("tahun_terbit")String tahun_terbit,
                         @Field("username")String username);

    @POST("request/search-by-judul/{judul}")
    Call<List<requestDAO>> getRequestByJudul(@Path("judul") String judul);

    @POST("request/search-by-username/{username}")
    Call<List<requestDAO>> getRequestByUsername(@Path("username") String username);

    @DELETE("request/{judul}")
    Call<requestDAO> deleteRequest(@Path("judul")String judul);
}
