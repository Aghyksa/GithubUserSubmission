package com.aghyksa.submission.dicoding.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aghyksa.submission.dicoding.api.RetrofitClient
import com.aghyksa.submission.dicoding.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel(private val username: String) : ViewModel() {
    val following = MutableLiveData<ArrayList<User>>()

    fun setFollowing() {
        RetrofitClient.apiInstance
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        following.postValue(response.body())
                    }
                }
                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })
    }
    fun getFollowing(): LiveData<ArrayList<User>>{
        return following
    }
}