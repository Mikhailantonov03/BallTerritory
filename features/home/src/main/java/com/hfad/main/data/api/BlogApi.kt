package com.hfad.main.data.api

import com.hfad.module.BlogArticle
import retrofit2.http.GET

interface BlogApi {
    @GET("/articles")
    suspend fun getArticles(): List<BlogArticle>
}
