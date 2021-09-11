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
    private var isRequest: Boolean = false

    private var page: Int = 1
    private var pageSize: Int = 10

    init {
        beers.value = ArrayList<BeerData>()
    }

    fun canRequest(): Boolean {
        return !isRequest
    }

    fun wasFirstPage() = (page-1) == 1

    suspend fun getNextBeers() {
        isRequest = true
        withContext(Dispatchers.Default) {
            repository.getBeers(
                page = page,
                pageSize = pageSize,
                onSuccess = {
                    beers.postValue(it.toList())
                    isRequest = false
                    page++
                }, onError = {
                    isRequest = false
                    Log.e("RESULT", "Error")
                }
            )
        }
    }
}

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}