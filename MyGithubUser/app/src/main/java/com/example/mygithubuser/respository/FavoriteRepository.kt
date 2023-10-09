package com.example.mygithubuser.respository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mygithubuser.database.Favorite
import com.example.mygithubuser.database.FavoriteDao
import com.example.mygithubuser.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoritesDao : FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoritesDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoritesDao.getAllFavorite()

    fun getAllFavoriteName(username: String) : LiveData<Favorite> = mFavoritesDao.getAllFavoriteName(username)

    fun insert(favorite: Favorite) {
        executorService.execute { mFavoritesDao.insert(favorite) }
    }

    fun delete(favorite: Favorite) {
        executorService.execute { mFavoritesDao.delete(favorite) }
    }

    fun update(favorite: Favorite) {
        executorService.execute { mFavoritesDao.update(favorite) }
    }
}