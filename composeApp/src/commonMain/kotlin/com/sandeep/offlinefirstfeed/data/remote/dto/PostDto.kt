package com.sandeep.offlinefirstfeed.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val id: String,
    val author: UserDto,
    val text: String,
    val imageUrl: String? = null,
    val likeCount: Int = 0,
    val likedByMe: Boolean = false,
    val createdAtMillis: Long,
    val updatedAtMillis: Long,
)
