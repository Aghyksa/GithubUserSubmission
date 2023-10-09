package com.aghyksa.submission.dicoding.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aghyksa.submission.dicoding.api.RetrofitClient
import com.aghyksa.submission.dicoding.database.GithubDatabase
import com.aghyksa.submission.dicoding.model.DetailUserResponse
import com.aghyksa.submission.dicoding.database.entity.Favorite
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (private val username:String): ViewModel() {
    private val user = MutableLiveData<DetailUserResponse>()
    private val favorite = MutableLiveData<Favorite?>()

    fun switchFavorite(context:Context){
        user.value?.let { user ->
            val db = GithubDatabase.getInstance(context)
            favorite.value?.let {fav ->
                db?.favoriteDao()?.delete(fav)
            } ?: run {
                db?.favoriteDao()?.insert(Favorite(user.username,user.avatarUrl))
            }
            setFavorite(context)
        }
    }

    fun setFavorite(context: Context){
        val db = GithubDatabase.getInstance(context)
        val fav = db?.favoriteDao()?.getFavorite(username)
        favorite.postValue(fav)
    }

    fun getFavorite(): LiveData<Favorite?> {
        return favorite
    }

    fun setDetail(){
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse>{
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }
                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })
    }

    fun getDetail(): LiveData<DetailUserResponse> {
        return user
    }
}