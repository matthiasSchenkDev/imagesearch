package com.example.imagesearch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.domain.model.NetworkResult
import com.example.imagesearch.domain.usecase.GetImagesUseCase
import com.example.imagesearch.presentation.ImageEntity
import com.example.imagesearch.presentation.ImageEntityMapper
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val imageEntityMapper: ImageEntityMapper
) : ViewModel() {

    val resultsLiveEvent: LiveEvent<List<ImageEntity>> = LiveEvent()

    private var currentQuery: String = ""

    fun getImages(query: String) = viewModelScope.launch(Dispatchers.IO) {
        currentQuery = query
        val params = GetImagesUseCase.GetResultsUseCaseParams(query)
        getImagesUseCase.build(params).collect { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    val result = networkResult.data.map { imageEntityMapper.transform(it) }
                    resultsLiveEvent.postValue(result)
                }

                is NetworkResult.Error -> resultsLiveEvent.postValue(listOf())
            }
        }
    }

    fun getMoreImages() = getImages(currentQuery)

}