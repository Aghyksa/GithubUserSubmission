package com.aghyksa.submission.dicoding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aghyksa.submission.dicoding.databinding.ItemUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.aghyksa.submission.dicoding.database.entity.Favorite
import com.aghyksa.submission.dicoding.model.User

class FavoriteAdapter(val onItemClickCallback:FavoriteAdapter.OnItemClickCallback =
    object : FavoriteAdapter.OnItemClickCallback {override fun onItemClicked(data: Favorite) {}})
    : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val favorites = ArrayList<Favorite>()

    fun setList(favorites:List<Favorite>){
        this.favorites.clear()
        this.favorites.addAll(favorites)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val bind: ItemUserBinding) : RecyclerView.ViewHolder(bind.root){
        fun bind(favorite: Favorite){
            bind.root.setOnClickListener {
                onItemClickCallback.onItemClicked(favorite)
            }
            bind.apply {
                Glide.with(itemView)
                    .load(favorite.avatarUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivAvatar)
                tvName.text = favorite.username
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder((view))
    }

    override fun getItemCount(): Int = favorites.size

    interface OnItemClickCallback{
        fun onItemClicked(data: Favorite)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favorites[position])
    }
}