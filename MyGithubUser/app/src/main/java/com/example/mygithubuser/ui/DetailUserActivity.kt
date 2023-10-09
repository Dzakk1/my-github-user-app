package com.example.mygithubuser.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mygithubuser.R
import com.example.mygithubuser.data.response.DetailUserResponse
import com.example.mygithubuser.database.Favorite
import com.example.mygithubuser.databinding.ActivityDetailUserBinding
import com.example.mygithubuser.factory.FavoriteViewModelFactory
import com.example.mygithubuser.ui.favorite.FavoriteViewModel
import com.example.mygithubuser.ui.insert.FavoriteAddUpdateViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private var isEdit = false
    private var favorite: Favorite? = null
    private lateinit var favoriteAddUpdateViewModel: FavoriteAddUpdateViewModel

    private lateinit var binding: ActivityDetailUserBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val detailUserViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                DetailUserViewModel::class.java
            )

        val userName = intent.getStringExtra(EXTRA_USER)
        detailUserViewModel.detailUser(userName.toString())

        val avatar = intent.getStringExtra(EXTRA_AVATAR)

        val bundle = Bundle()
        bundle.putString(EXTRA_USER, userName)

        detailUserViewModel.userDetail.observe(this) { user ->
            setDetailUser(user)
        }

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        //favorite

        favoriteAddUpdateViewModel = obtainViewModel(this@DetailUserActivity)

        favorite = intent.getParcelableExtra(EXTRA_FAVORITE)

        if (favorite != null) {
            isEdit = true
        } else {
            favorite = Favorite()
        }

        favoriteAddUpdateViewModel.getAllFavoriteName(userName.toString())
            .observe(this, Observer { favoriteData ->
                if (favoriteData != null) {
                    isEdit = true
                    favorite = Favorite(favoriteData.id, favoriteData.username, favoriteData.avatarUrl)
                    Log.d("test 1", "Loged data != null")
                    binding.favButton.setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    favorite = Favorite()
                    Log.d("tes 2", "Loged data null")
                    binding.favButton.setImageResource(R.drawable.baseline_favorite_border_24)
                }
            })


        binding.favButton.setOnClickListener {
            favorite.let { favorite ->
                favorite!!.username = userName.toString()
                favorite.avatarUrl = avatar
            }
            if (isEdit) {
                favoriteAddUpdateViewModel.delete(favorite as Favorite)
                binding.favButton.setImageResource(R.drawable.baseline_favorite_border_24)
                showToast("Berhasil Menghapus Favorite")
            } else {
                favoriteAddUpdateViewModel.insert(favorite as Favorite)
                binding.favButton.setImageResource(R.drawable.baseline_favorite_24)
                showToast("Berhasil Menambahkan Favorite")
            }
            isEdit = true
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

    }

    private fun setDetailUser(user: DetailUserResponse) {
        binding.tvName.text = user.name
        binding.tvUsername.text = user.login
        binding.tvFollowers.text = resources.getString(R.string.follower, user.followers)
        binding.tvFollowing.text = resources.getString(R.string.following, user.following)
        Glide.with(this)
            .load(user.avatarUrl)
            .into(binding.imgUser)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    // favorite
    private fun obtainViewModel(activity: AppCompatActivity): FavoriteAddUpdateViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteAddUpdateViewModel::class.java)
    }

    private  fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_FAVORITE = "extra_favorite"
        const val EXTRA_AVATAR = "extra_avatar"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
    }

}

