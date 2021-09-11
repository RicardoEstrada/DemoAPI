package com.testing.wsconnector

import com.testing.wsconnector.models.BeerData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

public interface BeerApi {
    @GET("/v2/beers")
    fun getBeers(@Query("page") page: Int, @Query("per_page") pageSize: Int): Call<List<BeerData>>

    @GET("/v2/beers")
    fun getBeers(): Call<List<BeerData>>
}