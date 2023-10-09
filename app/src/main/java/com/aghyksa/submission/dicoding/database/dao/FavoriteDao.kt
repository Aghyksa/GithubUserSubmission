package com.aghyksa.submission.dicoding.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aghyksa.submission.dicoding.database.entity.Favorite


@Dao
interface FavoriteDao {
    @Insert
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * FROM favorite")
    fun getFavorites(): List<Favorite>

    @Query("SELECT * FROM favorite WHERE username = :username LIMIT 1")
    fun getFavorite(username: String): Favorite?
}