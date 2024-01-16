package com.example.imagesearch.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.imagesearch.R
import com.example.imagesearch.presentation.components.ErrorComponent
import com.example.imagesearch.presentation.components.IconText
import com.example.imagesearch.presentation.event.DetailsEvent
import com.example.imagesearch.presentation.state.DetailsState

@Composable
fun DetailsScreen(
    state: DetailsState, onEvent: (DetailsEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.details)) },
                navigationIcon = {
                    IconButton(onClick = { onEvent(DetailsEvent.Back) }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .height(200.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    state.imageEntity?.let {
                        SubcomposeAsyncImage(
                            loading = { CircularProgressIndicator() },
                            model = it.fullImageUrl,
                            contentDescription = it.fullImageUrl,
                        )
                    } ?: ErrorComponent()
                }
            }
            IconText(
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.margin_medium),
                    top = dimensionResource(id = R.dimen.margin_medium),
                    end = dimensionResource(id = R.dimen.margin_medium)
                ),
                iconResId = R.drawable.ic_person,
                text = state.imageEntity?.userName ?: ""
            )
            IconText(
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.margin_medium),
                    top = dimensionResource(id = R.dimen.margin_medium),
                    end = dimensionResource(id = R.dimen.margin_medium)
                ),
                iconResId = R.drawable.ic_label,
                text = state.imageEntity?.tags ?: ""
            )
            Row(
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.margin_medium),
                    top = dimensionResource(id = R.dimen.margin_big),
                    end = dimensionResource(id = R.dimen.margin_medium)
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.margin_medium))
            ) {
                IconText(
                    iconResId = R.drawable.ic_like,
                    text = state.imageEntity?.numLikes.toString()
                )
                IconText(
                    iconResId = R.drawable.ic_chat,
                    text = state.imageEntity?.numComments.toString()
                )
                IconText(
                    iconResId = R.drawable.ic_download,
                    text = state.imageEntity?.numDownloads.toString()
                )
            }
        }
    }
}