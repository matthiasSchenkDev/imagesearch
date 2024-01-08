package com.example.imagesearch.presentation.viewmodel.actions

sealed class DetailsAction {
    data class Load(val id: Int) : ImagesListAction()

}