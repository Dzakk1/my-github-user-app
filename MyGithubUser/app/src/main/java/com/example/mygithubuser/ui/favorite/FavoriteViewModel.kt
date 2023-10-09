package com.example.mygithubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.database.Favorite
import com.example.mygithubuser.respository.FavoriteRepository

class FavoriteViewModel(application: Application) :ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllfavorite(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorites()

}