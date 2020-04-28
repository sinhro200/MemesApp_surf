package com.sinhro.memesapp_surf.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sinhro.memesapp_surf.model.memes.MemeInfo

@Database(entities = [MemeInfo::class], version = DATABASE_VERSION)
abstract class MemesRoomDatabase : RoomDatabase() {
    companion object{
        @Volatile
        private var INSTANCE: MemesRoomDatabase? = null

        fun getDatabase(context: Context): MemesRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create database here
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MemesRoomDatabase::class.java,
                    DATABASE_TABLE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun memesDao() : MemeDao
}