package com.aghyksa.submission.dicoding.model

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
    @SerializedName("login") val username: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("followers_url") val followersUrl: String,
    @SerializedName("following_url") val followingUrl: String,
    val name: String,
    val following: Int,
    val followers: Int
)