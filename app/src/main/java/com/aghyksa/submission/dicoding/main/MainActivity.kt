package com.aghyksa.submission.dicoding.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.aghyksa.submission.dicoding.databinding.ActivityMainBinding
import com.aghyksa.submission.dicoding.detail.DetailActivity
import com.aghyksa.submission.dicoding.model.User
import com.aghyksa.submission.dicoding.adapter.UserAdapter
import com.aghyksa.submission.dicoding.utils.GenericViewModelFactory
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.aghyksa.submission.dicoding.R
import com.aghyksa.submission.dicoding.datastore.SettingPreferences
import com.aghyksa.submission.dicoding.datastore.dataStore
import com.aghyksa.submission.dicoding.favorite.FavoriteActivity
import com.aghyksa.submission.dicoding.setting.SettingActivity

class MainActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var pref :SettingPreferences
    private val viewModel: MainViewModel by viewModels{
        GenericViewModelFactory.create(
            MainViewModel(pref)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        pref = SettingPreferences.getInstance(application.dataStore)
        setContentView(bind.root)
        setupViewModel()
        setupSearch()
        setupUserRecyclerView()
        setUsers()
    }

    private fun setupViewModel() {
        with(viewModel) {
            getUsers().observe(this@MainActivity) {
                if (it != null) {
                    adapter.setUsers(it)
                    showLoading(false)
                }
            }
            getThemeSettings().observe(this@MainActivity) { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu!!.findItem(R.id.menu_favorite)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            menuItem.setIcon(R.drawable.outline_favorite_24_white)
        } else {
            menuItem.setIcon(R.drawable.outline_favorite_24)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            R.id.menu_setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
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