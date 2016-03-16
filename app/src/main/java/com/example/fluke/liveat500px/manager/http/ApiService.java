package com.example.fluke.liveat500px.manager.http;

import com.example.fluke.liveat500px.deo.PhotoItemCollectionDeo;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by FLUKE on 2/19/2016 AD.
 */
public interface ApiService {

    @POST("list")
    Call<PhotoItemCollectionDeo> loadPhotoList();

    @POST("list/after/{id}")
    Call<PhotoItemCollectionDeo> loadPhotoListAfterId(@Path("id") int id);

    @POST("list/before/{id}")
    Call<PhotoItemCollectionDeo> loadPhotoListBeforeId(@Path("id") int id);
}
