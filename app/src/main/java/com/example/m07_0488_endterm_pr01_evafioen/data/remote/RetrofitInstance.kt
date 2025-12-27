package com.example.m07_0488_endterm_pr01_evafioen.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private var BASE_URL = "https://api.spacexdata.com/v4/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: RocketApiService by lazy {
        retrofit.create(RocketApiService::class.java)
    }
    fun overrideApiUrl(url: String) {
        BASE_URL = url
    }
}
