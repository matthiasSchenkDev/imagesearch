package com.example.imagesearch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.domain.model.NetworkResult
import com.example.imagesearch.domain.usecase.GetImagesUseCase
import com.example.imagesearch.presentation.DispatcherProvider
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

    val resultListLiveEvent: LiveEvent<List<ImageEntity>> = LiveEvent()

    private var currentQuery: String = ""

    fun getImages(query: String) = viewModelScope.launch(dispatcherProvider.io()) {
        currentQuery = query
        val params = GetImagesUseCase.Params(query)
        getImagesUseCase.build(params).collect { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    val result = networkResult.data.map { imageEntityMapper.transform(it) }
                    resultListLiveEvent.postValue(result)
                }

                is NetworkResult.Error -> {}
            }
        }
    }

    fun getMoreImages() = getImages(currentQuery)

}