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
import com.example.imagesearch.presentation.screens.DetailsScreen
import com.example.imagesearch.presentation.screens.ImagesScreen
import com.example.imagesearch.presentation.viewmodel.DetailsViewModel
import com.example.imagesearch.presentation.viewmodel.ImagesListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageSearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val imagesListViewModel = hiltViewModel<ImagesListViewModel>()
            val detailsViewModel = hiltViewModel<DetailsViewModel>()

            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.LIST) {
                composable(Routes.LIST) {
                    val state by imagesListViewModel.imagesListState.collectAsState()
                    ImagesScreen(state, onEvent = {
                        when (it) {
                            is ImagesListEvent.LoadMore -> imagesListViewModel.getMoreImages()
                            is ImagesListEvent.OpenDetails -> {
                                detailsViewModel.getImage(it.id)
                                navController.navigate(Routes.DETAIL)
                            }
                        }
                    })
                }
                composable(Routes.DETAIL) {
                    val state by detailsViewModel.detailsState.collectAsState()
                    DetailsScreen(state)
                }
            }
            imagesListViewModel.getImages()
        }
    }
}