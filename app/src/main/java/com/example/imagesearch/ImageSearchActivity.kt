package com.example.imagesearch

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.imagesearch.presentation.event.ImagesListEvent
import com.example.imagesearch.presentation.navigation.Routes
import com.example.imagesearch.presentation.screens.ImagesScreen
import com.example.imagesearch.presentation.viewmodel.ImagesListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageSearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val imagesListViewModel = hiltViewModel<ImagesListViewModel>()
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.LIST) {
                composable(Routes.LIST) {
                    val state by imagesListViewModel.imagesListState.collectAsState()
                    ImagesScreen(state, onEvent = {
                        when (it) {
                            ImagesListEvent.LOAD_MORE -> imagesListViewModel.getMoreImages()
                        }
                    })
                }
            }
            imagesListViewModel.getImages()
        }
    }
}