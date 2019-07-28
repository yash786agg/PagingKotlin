package com.app.network.main

import com.app.model.main.MainModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApi
{
    @GET("rest/")
    fun fetchImageDataAsync(@Query("method") method: String, @Query("api_key") apiKey: String,
                            @Query("tags") tags: String, @Query("page") page: Int,
                            @Query("per_page") perPage: Int, @Query("format") format: String,
                            @Query("nojsoncallback") noJsonCallback: Long): Deferred<Response<MainModel>>
}