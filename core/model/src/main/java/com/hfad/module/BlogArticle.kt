package com.hfad.module

import kotlinx.serialization.Serializable

@Serializable
data class BlogArticle(
    val title: String,
    val date: String,
    val url: String,
    val imageUrl: String
)
