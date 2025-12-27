package com.example.m07_0488_endterm_pr01_evafioen.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RocketDao {

    @Query("SELECT * FROM rockets")
    suspend fun getAll(): List<RocketEntity>

    @Query("SELECT * FROM rockets WHERE id = :rocketId")
    suspend fun getRocketById(rocketId: String): RocketEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rockets: List<RocketEntity>)

    @Query("DELETE FROM rockets")
    suspend fun deleteAll()
}