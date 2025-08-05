package com.hfad.main.domain.repository

import com.hfad.module.BlogArticle

interface BlogRepository {
    suspend fun getArticles(): List<BlogArticle>
}
