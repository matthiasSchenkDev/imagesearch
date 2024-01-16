package com.example.imagesearch.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.imagesearch.R
import com.example.imagesearch.presentation.components.ErrorComponent
import com.example.imagesearch.presentation.components.ImagesList
import com.example.imagesearch.presentation.event.ImagesListEvent
import com.example.imagesearch.presentation.state.ImagesListState

@Composable
fun ImagesScreen(imagesListState: ImagesListState, onEvent: (ImagesListEvent) -> Unit) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
            )
        }
    ) { paddingValues ->
        var searchText by remember { mutableStateOf(imagesListState.query) }
        val focusManager = LocalFocusManager.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TextField(modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.margin_medium)),
                value = searchText,
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (!imagesListState.isLoading) {
                            onEvent(ImagesListEvent.Search(searchText))
                            focusManager.clearFocus()
                        }
                    }
                ),
                onValueChange = {
                    searchText = it
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = stringResource(
                            id = R.string.action_search
                        )
                    )
                })
            imagesListState.images?.let {
                ImagesList(
                    imagesListState,
                    onBottomReached = { onEvent(ImagesListEvent.LoadMore) },
                    onItemClick = {
                        onEvent(ImagesListEvent.OpenDetails(it))
                    }
                )
            } ?: ErrorComponent()
        }
    }
}