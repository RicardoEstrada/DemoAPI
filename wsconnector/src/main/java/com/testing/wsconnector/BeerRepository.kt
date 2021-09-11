package com.testing.wsconnector

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.testing.wsconnector.models.BeerData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BeerRepository() {
    private lateinit var beersList: List<BeerData>

    suspend fun getBeers(onSuccess: (List<BeerData>) -> Unit, onError: (String) -> Unit) {
        val connector: WSConnector = WSConnector()
        val apiIntercafe = connector.retrofitHelper.create(BeerApi::class.java).getBeers()

        apiIntercafe.enqueue(object : Callback<List<BeerData>?> {
            override fun onResponse(
                call: Call<List<BeerData>?>,
                response: Response<List<BeerData>?>
            ) {
                response.body()?.let {
                    Log.d("-- RESPONSE OK", Gson().toJson(it))
                    onSuccess.invoke(it)
                }
            }

            override fun onFailure(call: Call<List<BeerData>?>, t: Throwable) {
                t?.message?.let {
                    Log.d("-- RESPONSE FAIL", t?.let {it.message} ?: "General Error")
                    onError.invoke(it)
                }
            }
        })
    }
}