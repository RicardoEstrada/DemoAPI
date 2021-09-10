package com.testing.demoapi

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testing.wsconnector.BeerRepository
import com.testing.wsconnector.models.BeerData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeViewModel: ViewModel() {
    private val repository: BeerRepository by lazy { BeerRepository() }

    val beers: MutableLiveData<List<BeerData>> = MutableLiveData<List<BeerData>>()

    suspend fun getBeers() {
        withContext(Dispatchers.Default) {
            repository.getBeers(
                onSuccess = {
                    beers.postValue(it)
                }, onError = {
                    Log.e("RESULT", "Error")
                }
            )
        }
    }
}