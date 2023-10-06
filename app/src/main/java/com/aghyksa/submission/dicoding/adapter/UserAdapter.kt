package com.aghyksa.submission.dicoding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aghyksa.submission.dicoding.databinding.ItemUserBinding
import com.aghyksa.submission.dicoding.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class UserAdapter(val onItemClickCallback: OnItemClickCallback =
    object : OnItemClickCallback {override fun onItemClicked(data: User) {}})
    : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val users = ArrayList<User>()

    fun setUsers(users:ArrayList<User>){
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val bind: ItemUserBinding) : RecyclerView.ViewHolder(bind.root){
        fun bind(user: User){
            bind.apply {
                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivAvatar)
                tvName.text = user.username
                root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(user)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: User)
    }
}