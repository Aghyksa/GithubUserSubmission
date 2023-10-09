package com.aghyksa.submission.dicoding.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aghyksa.submission.dicoding.database.dao.FavoriteDao
import com.aghyksa.submission.dicoding.database.entity.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {
    companion object {
        private val DB_NAME = "github_db"
        private var instance: GithubDatabase?=null
        @Synchronized
        fun getInstance(context: Context): GithubDatabase? {
            if(instance ==null){
                instance = Room
                    .databaseBuilder(context.applicationContext,
                        GithubDatabase::class.java, DB_NAME
                    )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
    abstract fun favoriteDao(): FavoriteDao
}