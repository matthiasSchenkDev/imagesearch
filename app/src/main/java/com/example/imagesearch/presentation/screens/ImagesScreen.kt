package com.example.imagesearch.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.imagesearch.presentation.components.ImagesList
import com.example.imagesearch.presentation.event.ImagesListEvent
import com.example.imagesearch.presentation.state.ImagesListState

@Composable
fun ImagesScreen(imagesListState: ImagesListState, onEvent: (ImagesListEvent) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ImagesList(imagesListState, onBottomReached = { onEvent(ImagesListEvent.LOAD_MORE) })
    }
}