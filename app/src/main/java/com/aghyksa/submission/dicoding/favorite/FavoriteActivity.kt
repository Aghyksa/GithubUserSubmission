package com.aghyksa.submission.dicoding.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aghyksa.submission.dicoding.adapter.FavoriteAdapter
import com.aghyksa.submission.dicoding.database.entity.Favorite
import com.aghyksa.submission.dicoding.databinding.ActivityFavoriteBinding
import com.aghyksa.submission.dicoding.detail.DetailActivity
import com.aghyksa.submission.dicoding.utils.GenericViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var bind: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels {
        GenericViewModelFactory.create(FavoriteViewModel())
    }
    private lateinit var adapter: FavoriteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setupUserRecyclerView()
        setupViewModel()
    }

    override fun onStart() {
        showLoading(true)
        viewModel.setFavorites(this)
        super.onStart()
    }

    private fun setupViewModel() {
        viewModel.getFavorites().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    private fun setupUserRecyclerView() {
        adapter = FavoriteAdapter(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Favorite) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.username)
                    startActivity(it)
                }
            }
        })
        with(bind) {
            rvFav.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFav.adapter = adapter
        }
    }

    private fun showLoading(state: Boolean){
        if (state){
            bind.progressBar.visibility = View.VISIBLE
        }else{
            bind.progressBar.visibility = View.GONE
        }
    }
}