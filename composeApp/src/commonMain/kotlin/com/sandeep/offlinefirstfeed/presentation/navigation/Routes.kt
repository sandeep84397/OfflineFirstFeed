package com.sandeep.offlinefirstfeed.presentation.navigation

import com.sandeep.kmpcore.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data object FeedRoute : Route

@Serializable
data class PostDetailRoute(val postId: String) : Route

@Serializable
data object CreatePostRoute : Route
