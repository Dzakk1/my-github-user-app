package com.example.mygithubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithubuser.data.response.FollowingUserResponseItem
import com.example.mygithubuser.databinding.ItemUsernameBinding

class FollowingAdapter : ListAdapter<FollowingUserResponseItem, FollowingAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUsernameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        val following = getItem(position)
        holder.bind(following)
    }

    class MyViewHolder (val binding: ItemUsernameBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(following : FollowingUserResponseItem) {
            binding.tvUsername.text = following.login
            Glide.with(itemView)
                .load(following.avatarUrl)
                .into(binding.imgUser)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowingUserResponseItem>(){
            override fun areItemsTheSame(
                oldItem: FollowingUserResponseItem,
                newItem: FollowingUserResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FollowingUserResponseItem,
                newItem: FollowingUserResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}