package com.example.mygithubuser.data.retrofit

import com.example.mygithubuser.data.response.DetailUserResponse
import com.example.mygithubuser.data.response.FollowerUserResponseItem
import com.example.mygithubuser.data.response.FollowingUserResponseItem
import com.example.mygithubuser.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getUser(@Query("q") query: String): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username : String): Call<DetailUserResponse>


    @GET("/users/{username}/followers")
    fun getUserFollowers(@Path("username") username : String): Call<List<FollowerUserResponseItem>>


    @GET("/users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<FollowingUserResponseItem>>
}