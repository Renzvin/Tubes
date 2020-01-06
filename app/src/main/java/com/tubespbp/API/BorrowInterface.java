package com.tubespbp.API;

import com.tubespbp.DAO.borrowDAO;
import com.tubespbp.DAO.requestDAO;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BorrowInterface {
    @GET("borrow/show")
    Call<List<borrowDAO>> getBorrow();

    @POST("borrow/create")
    @FormUrlEncoded
    Call<String> addBorrow(@Field("username")String username,
                           @Field("judul")String judul,
                           @Field("tanggal_kembali") String tanggal_kembali);

    @PUT("borrow/{judul}")
    @FormUrlEncoded
    Call<String> putBorrow(@Field("username") String username,
                              @Path("judul") String judul,
                              @Field("tanggal_kembali") String tanggal_kembali);

    @POST("borrow/search-by-judul/{judul}")
    Call<borrowDAO> getBorrowByJudul(@Path("judul") String judul);

    @POST("borrow/search-by-username/{username}")
    Call<List<borrowDAO>> getBorrowByUsername(@Path("username") String username);

    @DELETE("borrow/{judul}")
    Call<borrowDAO> deleteBorrow(@Path("judul")String judul);
}
