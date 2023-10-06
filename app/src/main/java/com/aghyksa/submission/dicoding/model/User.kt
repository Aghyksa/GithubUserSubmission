package com.aghyksa.submission.dicoding.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login") val username: String,
    @SerializedName("avatar_url") val avatarUrl: String
)
