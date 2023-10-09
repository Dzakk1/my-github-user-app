package com.example.mygithubuser.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithubuser.data.response.ItemsItem
import com.example.mygithubuser.database.Favorite
import com.example.mygithubuser.databinding.ActivityFavoriteBinding
import com.example.mygithubuser.databinding.ItemUsernameBinding
import com.example.mygithubuser.helper.FavoriteDiffCallback
import com.example.mygithubuser.ui.DetailUserActivity

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private val listFavorite = ArrayList<Favorite>()
    fun setListFavorite(listFavorite: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorite, listFavorite)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(listFavorite)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val binding = ItemUsernameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    class FavoriteViewHolder (val binding: ItemUsernameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite : Favorite) {
            binding.tvUsername.text = favorite.username
            Glide.with(itemView)
                .load(favorite.avatarUrl)
                .into(binding.imgUser)

            val clickUser = binding.root.context
            itemView.setOnClickListener {
                val intent = Intent(clickUser, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USER, favorite.username)
                intent.putExtra(DetailUserActivity.EXTRA_AVATAR, favorite.avatarUrl)
                clickUser.startActivity(intent)
            }
        }
    }
}
