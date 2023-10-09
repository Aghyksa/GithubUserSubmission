package com.aghyksa.submission.dicoding.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aghyksa.submission.dicoding.model.User
import com.aghyksa.submission.dicoding.model.UserResponse
import com.aghyksa.submission.dicoding.api.RetrofitClient
import com.aghyksa.submission.dicoding.datastore.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences): ViewModel() {
    private val users = MutableLiveData<ArrayList<User>>()
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
    fun setUsers() {
        RetrofitClient.apiInstance
            .getUsers()
            .enqueue(object : Callback<List<User>>{
                override fun onResponse(
                    call: Call<List<User>>,
                    response: Response<List<User>>
                ) {
                    if (response.isSuccessful){
                        users.postValue((response.body() as ArrayList<User>))
                    }
                }
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })
    }
    fun setSearchUsers(query: String) {
        RetrofitClient.apiInstance
            .getSearchUser(query)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful){
                        users.postValue(response.body()?.items)
                    }
                }
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }
    fun getUsers(): LiveData<ArrayList<User>>{
        return users
    }
}