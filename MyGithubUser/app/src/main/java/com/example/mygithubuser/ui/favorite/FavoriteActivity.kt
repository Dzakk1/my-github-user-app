package com.example.mygithubuser.ui.favorite

import android.app.Instrumentation.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.R
import com.example.mygithubuser.databinding.ActivityFavoriteBinding
import com.example.mygithubuser.factory.FavoriteViewModelFactory
import com.example.mygithubuser.ui.insert.FavoriteAddUpdateViewModel

class FavoriteActivity : AppCompatActivity() {

    private var _favoriteActivityBinding : ActivityFavoriteBinding? = null
    private val binding get() = _favoriteActivityBinding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _favoriteActivityBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getAllfavorite().observe(this) { favoriteList ->
            if(favoriteList != null) {
                adapter.setListFavorite(favoriteList)
            }
        }
        adapter = FavoriteAdapter()
        binding?.rvFavoriteUser?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavoriteUser?.setHasFixedSize(true)
        binding?.rvFavoriteUser?.adapter = adapter

    }

    override fun onDestroy() {
        super.onDestroy()
        _favoriteActivityBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity) : FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
}