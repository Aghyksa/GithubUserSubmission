package com.aghyksa.submission.dicoding.detail

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.aghyksa.submission.dicoding.R
import com.aghyksa.submission.dicoding.adapter.SectionPagerAdapter
import com.aghyksa.submission.dicoding.databinding.ActivityDetailBinding
import com.aghyksa.submission.dicoding.utils.GenericViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_USERNAME = "USERNAME"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var bind: ActivityDetailBinding
    private var username: String? = null
    private val viewModel: DetailViewModel by viewModels {
        GenericViewModelFactory.create(DetailViewModel(username!!))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)
        username = intent.getStringExtra(EXTRA_USERNAME)
        setupViewPager()
        setupViewModel()
        setDetail()
    }

    private fun setupViewPager() {
        with(bind){
            vpUser.adapter = SectionPagerAdapter(this@DetailActivity, username!!)
            TabLayoutMediator(tabs, vpUser) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    private fun setDetail(){
        username?.let {
            showLoading(true)
            viewModel.setDetail()
        }?:run{
            finish()
        }
    }

    private fun setupViewModel() {
        viewModel.getDetail().observe(this) {
            if (it != null) {
                with(bind) {
                    tvName.text = it.name
                    tvLogin.text = it.username
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivAvatar)
                }
                showLoading(false)
            }
        }
    }

    private fun showLoading(state: Boolean){
        if (state){
            bind.llProgress.visibility = View.VISIBLE
        }else{
            bind.llProgress.visibility = View.GONE
        }
    }
}