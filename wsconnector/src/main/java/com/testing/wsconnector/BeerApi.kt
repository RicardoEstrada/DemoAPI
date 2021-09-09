package com.testing.wsconnector

import com.testing.wsconnector.models.BeerData
import retrofit2.Call
import retrofit2.http.GET

public interface BeerApi {
    @GET("/v2/beers")
    fun getBeers(): Call<List<BeerData>>
}