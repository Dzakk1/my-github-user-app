package com.example.mygithubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithubuser.data.response.FollowerUserResponseItem
import com.example.mygithubuser.databinding.ItemUsernameBinding

class FollowerAdapter : ListAdapter <FollowerUserResponseItem, FollowerAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUsernameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val follower = getItem(position)
        holder.bind(follower)
    }

    class MyViewHolder (val binding: ItemUsernameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(follower : FollowerUserResponseItem) {
            binding.tvUsername.text = follower.login
            Glide.with(itemView)
                .load(follower.avatarUrl)
                .into(binding.imgUser)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowerUserResponseItem>() {
            override fun areItemsTheSame(
                oldItem: FollowerUserResponseItem,
                newItem: FollowerUserResponseItem
            ): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(
                oldItem: FollowerUserResponseItem,
                newItem: FollowerUserResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}