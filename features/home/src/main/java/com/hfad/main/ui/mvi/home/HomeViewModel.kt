package com.hfad.main.ui.mvi.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.main.domain.usecase.GetArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<HomeSingleEvent>()
    val event: SharedFlow<HomeSingleEvent> = _event.asSharedFlow()

    init {

        onIntent(HomeIntent.LoadArticles)
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadArticles -> loadArticles()
            is HomeIntent.OnPassClicked -> {
                viewModelScope.launch {
                    _event.emit(HomeSingleEvent.NavigateToPass)
                }
            }
            is HomeIntent.OnArticleClicked -> {
                viewModelScope.launch {
                    _event.emit(HomeSingleEvent.NavigateToArticle(intent.url))
                }
            }
        }
    }

    private fun loadArticles() {
        viewModelScope.launch {
            try {
                val articles = getArticlesUseCase()
                _state.value = _state.value.copy(articles = articles, error = null)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "Неизвестная ошибка")
            }
        }
    }
}

