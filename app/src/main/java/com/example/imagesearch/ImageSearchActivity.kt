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
import com.example.imagesearch.presentation.event.DetailsEvent
import com.example.imagesearch.presentation.event.ImagesListEvent
import com.example.imagesearch.presentation.navigation.Routes
import com.example.imagesearch.presentation.screens.DetailsScreen
import com.example.imagesearch.presentation.screens.ImagesScreen
import com.example.imagesearch.presentation.viewmodel.DetailsViewModel
import com.example.imagesearch.presentation.viewmodel.ImagesListViewModel
import com.example.imagesearch.presentation.viewmodel.actions.DetailsAction
import com.example.imagesearch.presentation.viewmodel.actions.ImagesListAction
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
                            is ImagesListEvent.Search -> imagesListViewModel.onEvent(
                                ImagesListAction.Search(it.text)
                            )

                            is ImagesListEvent.LoadMore ->
                                imagesListViewModel.onEvent(ImagesListAction.LoadMore)

                            is ImagesListEvent.OpenDetails -> {
                                detailsViewModel.onEvent(DetailsAction.Load(it.id))
                                navController.navigate(Routes.DETAIL)
                            }
                        }
                    })
                }
                composable(Routes.DETAIL) {
                    val state by detailsViewModel.detailsState.collectAsState()
                    DetailsScreen(state, onEvent = {
                        when (it) {
                            is DetailsEvent.Back -> navController.navigateUp()
                        }
                    })
                }
            }
            imagesListViewModel.getImages()
        }
    }
}