package com.tubespbp.API;

import com.tubespbp.DAO.accountDAO;
import com.tubespbp.DAO.bukuDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BukuInterface {
    @GET("buku/show")
    Call<List<bukuDAO>> getBuku();

    @POST("buku/create")
    @FormUrlEncoded
    Call<String> addBuku(@Field("judul")String judul,
                            @Field("pengarang")String pengarang,
                            @Field("penerbit")String penerbit,
                            @Field("tahun_terbit")String tahun_terbit);

    @POST("buku/search/{judul}")
    Call<List<bukuDAO>> getBuku(@Path("judul") String judul);

    @DELETE("buku/{judul}")
    Call<bukuDAO> deleteBuku(@Path("judul")String judul);
}
