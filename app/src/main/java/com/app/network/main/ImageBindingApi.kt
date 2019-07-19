package com.app.network.main

import com.app.model.main.PhotoSizes
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageBindingApi {

    @GET("rest/")
    fun fetchSingleImageAsync(@Query("method") method: String, @Query("api_key") apiKey: String,
                            @Query("photo_id") tags: String,@Query("format") format: String,
                            @Query("nojsoncallback") nojsoncallback: Long): Deferred<Response<PhotoSizes>>

}