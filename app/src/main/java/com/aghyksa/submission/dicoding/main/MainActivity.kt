package com.aghyksa.submission.dicoding.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.aghyksa.submission.dicoding.databinding.ActivityMainBinding
import com.aghyksa.submission.dicoding.detail.DetailActivity
import com.aghyksa.submission.dicoding.model.User
import com.aghyksa.submission.dicoding.adapter.UserAdapter
import com.aghyksa.submission.dicoding.utils.GenericViewModelFactory
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainBinding
    private lateinit var adapter: UserAdapter

    private val viewModel: MainViewModel by viewModels{
        GenericViewModelFactory.create(
            MainViewModel()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setupViewModel()
        setupSearch()
        setupUserRecyclerView()
        setUsers()
    }

    private fun setupViewModel() {
        viewModel.getUsers().observe(this@MainActivity) {
            if (it != null) {
                adapter.setUsers(it)
                showLoading(false)
            }
        }
    }

    private fun setupSearch() {
        with(bind) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    setUsers(searchView.text.toString())
                    false
                }
        }
    }

    private fun setupUserRecyclerView() {
        adapter = UserAdapter(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.username)
                    startActivity(it)
                }
            }
        })
        with(bind) {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.adapter = adapter
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            bind.llProgress.visibility = View.VISIBLE
        } else {
            bind.llProgress.visibility = View.GONE
        }
    }

    private fun setUsers(username:String?=null) {
        showLoading(true)
        with(viewModel) {
            username?.let {
                setSearchUsers(it)
            }?:run{
                setUsers()
            }
        }
    }
}