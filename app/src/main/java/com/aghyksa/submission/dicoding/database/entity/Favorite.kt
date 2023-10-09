package com.aghyksa.submission.dicoding.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey val username: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String
)