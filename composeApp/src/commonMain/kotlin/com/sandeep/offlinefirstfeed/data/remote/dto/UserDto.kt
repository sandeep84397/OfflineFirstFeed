package com.sandeep.offlinefirstfeed.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val displayName: String,
    val avatarUrl: String? = null,
)
