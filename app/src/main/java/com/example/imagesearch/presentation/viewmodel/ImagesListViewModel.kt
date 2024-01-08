package com.example.imagesearch.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.domain.model.NetworkResult
import com.example.imagesearch.domain.usecase.GetImagesUseCase
import com.example.imagesearch.presentation.common.DispatcherProvider
import com.example.imagesearch.presentation.mapper.ImageEntityMapper
import com.example.imagesearch.presentation.state.ImagesListState
import com.example.imagesearch.presentation.viewmodel.actions.ImagesListAction
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
        initialValue = ImagesListState(INITIAL_QUERY)
    )

    fun onEvent(event: ImagesListAction) {
        when (event) {
            is ImagesListAction.Search -> getImages(event.text)
            is ImagesListAction.LoadMore -> getMoreImages()
            else -> {}
        }
    }

    fun getImages(query: String = INITIAL_QUERY) = viewModelScope.launch(dispatcherProvider.io()) {
        savedStateHandle[STATE_HANDLE_KEY_IMAGES] =
            imagesListState.value.copy(
                query = query,
                images = if (query == imagesListState.value.query) {
                    imagesListState.value.images
                } else listOf(),
                isLoading = true
            )
        val params = GetImagesUseCase.Params(query)
        getImagesUseCase.build(params).collect { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    val result = networkResult.value.map { imageEntityMapper.transform(it) }
                    savedStateHandle[STATE_HANDLE_KEY_IMAGES] =
                        imagesListState.value.copy(images = result, isLoading = false)
                }

                is NetworkResult.Error -> savedStateHandle[STATE_HANDLE_KEY_IMAGES] =
                    imagesListState.value.copy(images = null, isLoading = false)
            }
        }
    }

    fun getMoreImages() = getImages(imagesListState.value.query)

}