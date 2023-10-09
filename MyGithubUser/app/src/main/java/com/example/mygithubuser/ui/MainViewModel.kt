package com.example.mygithubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.mygithubuser.data.response.GithubResponse
import com.example.mygithubuser.data.response.ItemsItem
import com.example.mygithubuser.data.retrofit.ApiConfig
import com.example.mygithubuser.ui.dark.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
//
//    fun getTheme() = preferences.getThemeSetting().asLiveData()

    private val _user = MutableLiveData<List<ItemsItem>>()
    val user: LiveData<List<ItemsItem>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val  TAG = "MainViewModel"
    }

    init {
        findUser("jeck")
    }

    fun findUser(username: String) {

        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                        _user.value = response.body()?.items
                } else {
                    Log.e(TAG,"onFailure: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}