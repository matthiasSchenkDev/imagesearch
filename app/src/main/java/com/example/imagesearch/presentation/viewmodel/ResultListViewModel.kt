package com.example.imagesearch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.domain.model.NetworkResult
import com.example.imagesearch.domain.usecase.GetImagesUseCase
import com.example.imagesearch.presentation.common.DispatcherProvider
import com.example.imagesearch.presentation.mapper.ImageEntityMapper
import com.example.imagesearch.presentation.model.ImageEntity
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultListViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val imageEntityMapper: ImageEntityMapper,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val resultListLiveEvent: LiveEvent<List<ImageEntity>?> = LiveEvent()

    var currentResultsQuery: String = ""
        private set

    fun getImages(query: String) = viewModelScope.launch(dispatcherProvider.io()) {
        val params = GetImagesUseCase.Params(query)
        getImagesUseCase.build(params).collect { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    currentResultsQuery = query
                    val result = networkResult.value.map { imageEntityMapper.transform(it) }
                    resultListLiveEvent.postValue(result)
                }
                // Assumption: no detailed error handling required
                is NetworkResult.Error -> resultListLiveEvent.postValue(null)
            }
        }
    }

    fun getMoreImages() = getImages(currentResultsQuery)

}