package com.testing.wsconnector

import android.content.Context
import com.testing.wsconnector.models.BeerData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BeerRepository(var context: Context) {

    suspend fun getBeers(onSuccess: (List<BeerData>) -> Unit, onError: (String) -> Unit) {
        val connector: WSConnector = WSConnector(context)
        val apiIntercafe = connector.retrofitHelper.create(BeerApi::class.java).getBeers()

        apiIntercafe.enqueue(object : Callback<List<BeerData>?> {
            override fun onResponse(
                call: Call<List<BeerData>?>,
                response: Response<List<BeerData>?>
            ) {
                response.body()?.let { onSuccess.invoke(it) }
            }

            override fun onFailure(call: Call<List<BeerData>?>, t: Throwable) {
                t?.message?.let { onError.invoke(it) }
            }
        })
    }
}