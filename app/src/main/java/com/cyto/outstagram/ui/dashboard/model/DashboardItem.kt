package com.cyto.outstagram.ui.dashboard.model

import java.util.Date
import com.google.gson.annotations.SerializedName

data class UserPublic(val userId: String, val username: String, val fullName: String, val bio: String)

data class MediaItem(
    val url: String,  // Adjust property names as needed
    val type: String? // Example: Could be "image", "video", etc.  Nullable if it can be missing
)

data class UserPost(
    @SerializedName("post_id") val postId: String,
    @SerializedName("caption") val caption: String,
    @SerializedName("post_category") val postCategory: String,
    @SerializedName("datetime_posted") val datetimePosted: String,
    @SerializedName("author_user_id") val authorUserId: Int, // Or String, depending on your data
    @SerializedName("highlighted_by_author") val highlightedByAuthor: Boolean,
    @SerializedName("is_liked") val isLiked: Boolean,
    @SerializedName("media_urls") val mediaUrls: List<MediaItem>,
    @SerializedName("author") val author: String,
)

data class DashboardItem(val user: UserPublic, val posts: List<UserPost>)
