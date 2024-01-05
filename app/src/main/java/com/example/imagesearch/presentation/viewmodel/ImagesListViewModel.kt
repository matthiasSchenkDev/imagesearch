package com.example.imagesearch.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.domain.model.NetworkResult
import com.example.imagesearch.domain.usecase.GetImagesUseCase
import com.example.imagesearch.presentation.common.DispatcherProvider
import com.example.imagesearch.presentation.mapper.ImageEntityMapper
import com.example.imagesearch.presentation.state.ImagesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getImagesUseCase: GetImagesUseCase,
    private val imageEntityMapper: ImageEntityMapper,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    companion object {
        private const val STATE_HANDLE_KEY_IMAGES = "imagesList"
        private const val INITIAL_QUERY = "fruits"
    }

    val imagesListState = savedStateHandle.getStateFlow(
        key = STATE_HANDLE_KEY_IMAGES,
        initialValue = ImagesListState()
    )

    var currentResultsQuery: String = ""
        private set

    fun getImages(query: String = INITIAL_QUERY) = viewModelScope.launch(dispatcherProvider.io()) {
        savedStateHandle[STATE_HANDLE_KEY_IMAGES] =
            imagesListState.value.copy(isLoading = true)
        val params = GetImagesUseCase.Params(query)
        getImagesUseCase.build(params).collect { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    currentResultsQuery = query
                    val result = networkResult.value.map { imageEntityMapper.transform(it) }
                    savedStateHandle[STATE_HANDLE_KEY_IMAGES] =
                        imagesListState.value.copy(images = result, isLoading = false)
                }

                is NetworkResult.Error -> savedStateHandle[STATE_HANDLE_KEY_IMAGES] =
                    imagesListState.value.copy(images = null, isLoading = false)
            }
        }
    }

    fun getMoreImages() = getImages(currentResultsQuery)

}