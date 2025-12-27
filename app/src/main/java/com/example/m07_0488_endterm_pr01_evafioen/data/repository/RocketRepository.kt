package com.example.m07_0488_endterm_pr01_evafioen.data.repository

import android.content.Context
import com.example.m07_0488_endterm_pr01_evafioen.data.local.RocketDatabase
import com.example.m07_0488_endterm_pr01_evafioen.data.local.RocketEntity
import com.example.m07_0488_endterm_pr01_evafioen.data.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RocketRepository(private val context: Context) {
    private val rocketDao by lazy { RocketDatabase.getDatabase(context).rocketDao() }
    suspend fun getRockets(): List<RocketEntity> = withContext(Dispatchers.IO) {
        try {
            val remoteRockets = RetrofitInstance.api.getRockets()
            val entities = remoteRockets.map { dto ->
                RocketEntity(
                    id = dto.id,
                    name = dto.name,
                    country = dto.country,
                    description = dto.description,
                    imageUrl = dto.images.firstOrNull(),
                    active = dto.active,
                    firstFlight = dto.firstFlight,
                    costPerLaunch = dto.costPerLaunch,
                    successRatePct = dto.successRatePct,
                    wikipedia = dto.wikipedia
                )
            }
            rocketDao.deleteAll()
            rocketDao.insertAll(entities)
            return@withContext entities
        } catch (e: Exception) {
            val localData = rocketDao.getAll()
            if (localData.isNotEmpty()) {
                return@withContext localData
            }
            throw e
        }
    }

    suspend fun getRocketById(rocketId: String): RocketEntity? = withContext(Dispatchers.IO) {
        return@withContext rocketDao.getRocketById(rocketId)
    }
}