package com.aghyksa.submission.dicoding.adapter

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aghyksa.submission.dicoding.detail.FollowersFragment
import com.aghyksa.submission.dicoding.detail.FollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FollowersFragment(username)
            }
            1 -> {
                fragment = FollowingFragment(username)
            }
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}