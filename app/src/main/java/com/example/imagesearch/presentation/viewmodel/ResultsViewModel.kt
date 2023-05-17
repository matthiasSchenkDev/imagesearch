package com.example.imagesearch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.domain.model.Image
import com.example.imagesearch.domain.model.NetworkResult
import com.example.imagesearch.domain.usecase.GetResultsUseCase
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val getResultsUseCase: GetResultsUseCase
) : ViewModel() {

    val resultsLiveEvent: LiveEvent<List<Image>> = LiveEvent()

    fun getImages(query: String) = viewModelScope.launch(Dispatchers.IO) {
        val params = GetResultsUseCase.GetResultsUseCaseParams(query)
        getResultsUseCase.build(params).collect {
            when (it) {
                is NetworkResult.Success -> resultsLiveEvent.postValue(it.data)
                is NetworkResult.Error -> {}
            }

        }
    }

}