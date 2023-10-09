package com.example.mygithubuser.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.R
import com.example.mygithubuser.data.response.ItemsItem
import com.example.mygithubuser.databinding.ActivityMainBinding
import com.example.mygithubuser.factory.DarkModeViewModelFactory
import com.example.mygithubuser.ui.dark.DarkModeActivity
import com.example.mygithubuser.ui.dark.DarkModeViewModel
import com.example.mygithubuser.ui.dark.SettingPreferences
import com.example.mygithubuser.ui.dark.dataStore
import com.example.mygithubuser.ui.favorite.FavoriteActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter


//    private val viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        val darkMode = SettingPreferences.getInstance(application.dataStore)

        val darkModeViewModel = ViewModelProvider(this, DarkModeViewModelFactory(darkMode)).get(
            DarkModeViewModel::class.java
        )

        darkModeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        adapter = UserAdapter()
        binding.rvUser.adapter = adapter

        mainViewModel.user.observe(this) {listUser ->
            setUserData(listUser)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }




        with(binding) {

            searchView.setupWithSearchBar(searchBar)

            searchBar.inflateMenu(R.menu.menu_option)
            val menu = searchBar.menu
            val menuOption = menu.findItem(R.id.item_favorite)
            val darkModeOption = menu.findItem(R.id.item_darkMode)
            val darkModeActive = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

            if(darkModeActive == Configuration.UI_MODE_NIGHT_YES) {
                menuOption.setIcon(R.drawable.baseline_favorite_24_white)
                // kurang option menu
            } else {
                menuOption.setIcon(R.drawable.baseline_favorite_24)
            }

            menuOption.setOnMenuItemClickListener {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                true
            }

            darkModeOption.setOnMenuItemClickListener {
                val intent = Intent(this@MainActivity, DarkModeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                true
            }

            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    val userName = searchView.text.toString()
                    mainViewModel.findUser(userName)
                    searchView.hide()
                    false
                }
        }
    }


    private fun setUserData(user: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(user)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



}