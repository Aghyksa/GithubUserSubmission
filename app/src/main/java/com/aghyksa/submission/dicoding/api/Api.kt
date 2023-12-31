package com.aghyksa.submission.dicoding.api

import com.aghyksa.submission.dicoding.model.DetailUserResponse
import com.aghyksa.submission.dicoding.model.User
import com.aghyksa.submission.dicoding.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    companion object{
        private const val TOKEN = "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"
    }

    @GET("search/users")
    @Headers("Authorization: token $TOKEN")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users")
    @Headers("Authorization: token $TOKEN")
    fun getUsers(): Call<List<User>>

    @GET("users/{username}")
    @Headers("Authorization: token $TOKEN")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $TOKEN")
    fun getFollowers(
        @Path("username") username: String
    ):Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $TOKEN")
    fun getFollowing(
        @Path("username") username: String
    ):Call<ArrayList<User>>
}