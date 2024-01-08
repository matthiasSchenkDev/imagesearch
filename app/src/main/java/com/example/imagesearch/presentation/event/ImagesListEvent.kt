package com.example.imagesearch.presentation.event

sealed class ImagesListEvent {
    data class Search(val text: String) : ImagesListEvent()
    data object LoadMore : ImagesListEvent()
    data class OpenDetails(val id: Int) : ImagesListEvent()
}