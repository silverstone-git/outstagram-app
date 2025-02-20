package com.cyto.outstagram.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cyto.outstagram.ui.dashboard.model.DashboardItem
import com.cyto.outstagram.ui.dashboard.model.UserPost
import com.cyto.outstagram.ui.dashboard.model.UserPublic
import com.cyto.outstagram.ui.home.model.HomeItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DashboardViewModel : ViewModel() {


    private val _dashboardItem = MutableLiveData<DashboardItem>()
    val dashboardItem: LiveData<DashboardItem> = _dashboardItem

    init {
        // Initialize the data (e.g., fetch from a repository)
        loadDashboardItems()
    }

    private fun loadDashboardItems() {
        // Simulate loading data

        //user coming from auth / login request, stored to local cache when responded
        val user = UserPublic(
            userId = "12",
            username = "notbawa",
            fullName = "Bawa Singh",
            bio = "I am a bio"
        )
        val gson = Gson() // Initialize Gson
        val jsonString = """[{"post_id":"202278f4-3afa-4fc0-b589-d6a812dad925","caption":"Bawa Jesus","post_category":"lifestyle","datetime_posted":"2024-12-29T10:00:32","author_user_id":5,"highlighted_by_author":false,"is_liked":false,"media_urls":[{"url": "https://upload.wikimedia.org/wikipedia/commons/5/57/Shrimp_Jesus_example.jpg", "type": "image"}],"author":"notbawa"}]"""
        val userPosts = object : TypeToken<List<UserPost>>() {}.type
        val postResponse: List<UserPost> = gson.fromJson(jsonString, userPosts)

        _dashboardItem.value = DashboardItem(user = user, posts = postResponse)
    }
}