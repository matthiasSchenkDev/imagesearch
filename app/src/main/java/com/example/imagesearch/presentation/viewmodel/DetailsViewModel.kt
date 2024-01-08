package com.example.imagesearch.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.domain.model.NetworkResult
import com.example.imagesearch.domain.usecase.GetImageUseCase
import com.example.imagesearch.presentation.common.DispatcherProvider
import com.example.imagesearch.presentation.mapper.ImageEntityMapper
import com.example.imagesearch.presentation.state.DetailsState
import com.example.imagesearch.presentation.viewmodel.actions.DetailsAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getImageUseCase: GetImageUseCase,
    private val imageEntityMapper: ImageEntityMapper,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    companion object {
        private const val STATE_HANDLE_KEY_DETAILS = "details"
    }

    val detailsState = savedStateHandle.getStateFlow(
        key = STATE_HANDLE_KEY_DETAILS,
        initialValue = DetailsState()
    )

    fun getImage(id: Int) = viewModelScope.launch(dispatcherProvider.io()) {
        savedStateHandle[STATE_HANDLE_KEY_DETAILS] = detailsState.value.copy(isLoading = true)
        val params = GetImageUseCase.Params(id)
        getImageUseCase.build(params).collect { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    val result = imageEntityMapper.transform(networkResult.value)
                    savedStateHandle[STATE_HANDLE_KEY_DETAILS] =
                        detailsState.value.copy(imageEntity = result, isLoading = false)
                }

                is NetworkResult.Error -> savedStateHandle[STATE_HANDLE_KEY_DETAILS] =
                    detailsState.value.copy(imageEntity = null, isLoading = false)
            }
        }
    }

    fun onEvent(event: DetailsAction.Load) {
        when (event) {
            is DetailsAction.Load -> getImage(event.id)
        }
    }

}