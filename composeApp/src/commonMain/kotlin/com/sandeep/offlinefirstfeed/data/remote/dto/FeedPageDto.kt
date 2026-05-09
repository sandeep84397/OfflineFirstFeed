package com.sandeep.offlinefirstfeed.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class FeedPageDto(
    val posts: List<PostDto>,
    val nextOffset: Int?,
)
