package com.example.imagesearch.presentation.event

sealed class ImagesListEvent {
    data object LoadMore : ImagesListEvent()
    data class OpenDetails(val id: Int) : ImagesListEvent()
}