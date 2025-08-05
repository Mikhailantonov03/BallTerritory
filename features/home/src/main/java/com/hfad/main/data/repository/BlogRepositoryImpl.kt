package com.hfad.main.data.repository

import com.hfad.main.data.api.BlogApi
import com.hfad.main.domain.repository.BlogRepository
import com.hfad.module.BlogArticle
import javax.inject.Inject

class BlogRepositoryImpl @Inject constructor(
    private val api: BlogApi
) : BlogRepository {
    override suspend fun getArticles(): List<BlogArticle> = api.getArticles()
}
