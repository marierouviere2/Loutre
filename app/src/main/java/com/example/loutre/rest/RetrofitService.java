package com.example.loutre.rest;

import com.example.loutre.POJO.InstagramResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

// https://api.instagram.com/v1/tags/{tag-name}/media/recent?access_token=ACCESS-TOKEN

public interface RetrofitService {

    @GET("v1/tags/{tag_name}/media/recent") // searching posts by tags
    Call<InstagramResponse> getTagPhotos(@Path("tag_name") String tag_name,
                                         @Query("access_token") String access_token); // Necessary for every request
}
