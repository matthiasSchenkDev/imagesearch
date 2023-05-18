package com.example.imagesearch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.domain.model.NetworkResult
import com.example.imagesearch.domain.usecase.GetImageUseCase
import com.example.imagesearch.presentation.ImageEntity
import com.example.imagesearch.presentation.ImageEntityMapper
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getImageUseCase: GetImageUseCase,
    private val imageEntityMapper: ImageEntityMapper
) : ViewModel() {

    val imageResultLiveEvent: LiveEvent<ImageEntity?> = LiveEvent()

    fun getImage(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        val params = GetImageUseCase.Params(id)
        getImageUseCase.build(params).collect { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    val result = imageEntityMapper.transform(networkResult.data)
                    imageResultLiveEvent.postValue(result)
                }

                is NetworkResult.Error -> imageResultLiveEvent.postValue(null)
            }
        }
    }

}