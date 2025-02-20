package com.cyto.outstagram.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cyto.outstagram.ui.home.model.HomeItem

//class HomeViewModel : ViewModel() {
//
//    private val _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
//    }
//    val text: LiveData<String> = _text
//}

class HomeViewModel : ViewModel() {

    private val _homeItems = MutableLiveData<List<HomeItem>>()
    val homeItems: LiveData<List<HomeItem>> = _homeItems

    init {
        // Initialize the data (e.g., fetch from a repository)
        loadHomeItems()
    }

    private fun loadHomeItems() {
        // Simulate loading data
        val items = listOf(
            HomeItem(1, "Post 1", "Description 1"),
            HomeItem(2, "Post 2", "Description 2"),
            HomeItem(3, "Post 3", "Description 3")
        )
        _homeItems.value = items
    }
}