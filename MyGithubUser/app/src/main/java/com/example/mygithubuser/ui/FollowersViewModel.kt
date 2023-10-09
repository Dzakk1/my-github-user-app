package com.example.mygithubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.data.response.FollowerUserResponseItem
import com.example.mygithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    private val _follower = MutableLiveData<List<FollowerUserResponseItem>>()
    val follower: LiveData<List<FollowerUserResponseItem>> = _follower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val  TAG = "FollowersViewModel"
    }

    fun listFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<FollowerUserResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowerUserResponseItem>>,
                response: Response<List<FollowerUserResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _follower.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowerUserResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}