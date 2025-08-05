package com.hfad.main.ui.mvi.home

import com.hfad.module.BlogArticle

data class HomeState(
    val articles: List<BlogArticle> = emptyList(),
    val error: String? = null
)
