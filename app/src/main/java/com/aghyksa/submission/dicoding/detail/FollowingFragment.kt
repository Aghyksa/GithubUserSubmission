package com.aghyksa.submission.dicoding.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aghyksa.submission.dicoding.adapter.UserAdapter
import com.aghyksa.submission.dicoding.databinding.FragmentFollowBinding
import com.aghyksa.submission.dicoding.model.User
import com.aghyksa.submission.dicoding.utils.GenericViewModelFactory

class FollowingFragment(private val username:String): Fragment() {

    private lateinit var bind : FragmentFollowBinding

    private val viewModel: FollowingViewModel by viewModels{
        GenericViewModelFactory.create(
            FollowingViewModel(username)
        )
    }

    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind =  FragmentFollowBinding.inflate(inflater,container,false);
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentFollowBinding.bind(view)
        setupViewModel()
        setupRecyclerView()
        showLoading(true)
        viewModel.setFollowing()
    }

    private fun setupViewModel() {
        viewModel.getFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setUsers(it)
                showLoading(false)
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(requireContext(), DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.username)
                    startActivity(it)
                }
            }
        })
        with(bind){
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
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