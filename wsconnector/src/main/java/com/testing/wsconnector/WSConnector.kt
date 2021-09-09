package com.testing.wsconnector

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
*  This class create and manage API connection
* */
internal class WSConnector(val context: Context) {
    internal val retrofitHelper: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}