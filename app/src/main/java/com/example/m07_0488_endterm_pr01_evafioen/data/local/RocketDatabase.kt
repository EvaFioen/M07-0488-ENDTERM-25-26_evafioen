package com.example.m07_0488_endterm_pr01_evafioen.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RocketEntity::class], version = 1, exportSchema = false)
abstract class RocketDatabase : RoomDatabase() {

    abstract fun rocketDao(): RocketDao

    companion object {
        @Volatile
        private var INSTANCE: RocketDatabase? = null

        fun getDatabase(context: Context): RocketDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RocketDatabase::class.java,
                    "rocket_db"
                )
                .fallbackToDestructiveMigration(dropAllTables = true)
                .build().also { INSTANCE = it }
            }
        }
    }
}