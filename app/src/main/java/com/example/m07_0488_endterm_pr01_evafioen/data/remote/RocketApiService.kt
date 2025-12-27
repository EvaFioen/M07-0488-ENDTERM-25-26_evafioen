package com.example.m07_0488_endterm_pr01_evafioen.data.remote

import com.example.m07_0488_endterm_pr01_evafioen.data.model.RocketDto
import retrofit2.http.GET

/**
 * Interface que define los endpoints de la API de SpaceX.
 */
interface RocketApiService {
    @GET("rockets")
    suspend fun getRockets(): List<RocketDto>
}
