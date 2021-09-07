package com.testing.wsconnector

import android.content.Context
import retrofit2.Retrofit

/*
*  This class create and manage API connection
* */
class WSConnector(val context: Context) {
    private lateinit var retrofitHelper: Retrofit

    init {
        retrofitHelper = Retrofit.Builder()
            .baseUrl("")
            .build()
    }
}