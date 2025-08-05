package com.hfad.main.domain.usecase

import com.hfad.main.domain.repository.BlogRepository
import com.hfad.module.BlogArticle
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val repository: BlogRepository
) {
    suspend operator fun invoke(): List<BlogArticle> {
        return repository.getArticles()
    }
}