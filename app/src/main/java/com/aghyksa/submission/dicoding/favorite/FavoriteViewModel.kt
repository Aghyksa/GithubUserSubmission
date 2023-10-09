package com.aghyksa.submission.dicoding.favorite

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aghyksa.submission.dicoding.database.GithubDatabase
import com.aghyksa.submission.dicoding.database.entity.Favorite

class FavoriteViewModel : ViewModel() {
    private val favorites = MutableLiveData<List<Favorite>>()
    fun setFavorites(context:Context) {
        val db = GithubDatabase.getInstance(context)
        val fav = db?.favoriteDao()?.getFavorites()
        favorites.postValue(fav!!)
    }

    fun getFavorites(): LiveData<List<Favorite>>{
        return favorites
    }
}