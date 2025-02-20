package com.cyto.outstagram.ui.dashboard

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cyto.outstagram.ui.dashboard.model.DashboardItem
import com.cyto.outstagram.ui.dashboard.model.UserPost
import com.cyto.outstagram.ui.dashboard.model.UserPublic
import com.cyto.outstagram.ui.home.model.HomeItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import com.cyto.outstagram.AppStateViewModel
import com.cyto.outstagram.MainActivity
import com.cyto.outstagram.R
import com.cyto.outstagram.databinding.ActivityLoginBinding
import com.cyto.outstagram.util.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private val _dashboardItem = MutableLiveData<DashboardItem>()
    val dashboardItem: LiveData<DashboardItem> = _dashboardItem

    init {
        // Initialize the data (e.g., fetch from a repository)
        loadDashboardItems()
    }

    private fun loadDashboardItems() {

        val gson = Gson() // Initialize Gson

        //user coming from auth / login request, stored to local cache when responded
        val userJson = sharedPreferences.getString("user", null)
        if (userJson == null) {
            Log.d("DashboardViewModel", "User is null")
            return
        }
        val user = gson.fromJson(userJson, UserPublic::class.java)

        val jsonString = """[{"post_id":"202278f4-3afa-4fc0-b589-d6a812dad925","caption":"Bawa Jesus","post_category":"lifestyle","datetime_posted":"2024-12-29T10:00:32","author_user_id":5,"highlighted_by_author":false,"is_liked":false,"media_urls":[{"url": "https://upload.wikimedia.org/wikipedia/commons/5/57/Shrimp_Jesus_example.jpg", "type": "image"}],"author":"notbawa"}]"""
        val userPosts = object : TypeToken<List<UserPost>>() {}.type
        val postResponse: List<UserPost> = gson.fromJson(jsonString, userPosts)

        _dashboardItem.value = DashboardItem(user = user, posts = postResponse)
    }

}