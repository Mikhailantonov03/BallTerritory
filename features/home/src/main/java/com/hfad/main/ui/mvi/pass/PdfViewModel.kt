package com.hfad.main.ui.mvi.pass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.main.domain.usecase.LoadPdfUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PdfViewModel @Inject constructor(
    private val loadPdfUseCase: LoadPdfUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PdfState())
    val state: StateFlow<PdfState> = _state.asStateFlow()

    fun onIntent(intent: PdfIntent) {
        when (intent) {
            is PdfIntent.LoadPdf -> loadPdf(intent.url)
        }
    }

    private fun loadPdf(url: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val bitmaps = loadPdfUseCase(url)
                _state.value = PdfState(isLoading = false, bitmaps = bitmaps)
            } catch (e: Exception) {
                _state.value = PdfState(isLoading = false, error = e.message)
            }
        }
    }
}

