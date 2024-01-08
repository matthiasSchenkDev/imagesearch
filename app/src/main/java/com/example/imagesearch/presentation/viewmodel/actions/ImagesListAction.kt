package com.example.imagesearch.presentation.viewmodel.actions

sealed class ImagesListAction {
    data class Search(val text: String) : ImagesListAction()
    data object LoadMore : ImagesListAction()
}