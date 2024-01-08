package com.example.imagesearch.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.imagesearch.R
import com.example.imagesearch.presentation.model.ImageEntity
import com.example.imagesearch.presentation.state.ImagesListState

@Composable
fun ImagesList(
    state: ImagesListState,
    onBottomReached: () -> Unit,
    onItemClick: (imageId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val images = state.images ?: listOf()
    LazyColumn(
        modifier = modifier
    ) {
        items(images.size) { i ->
            if (i >= images.lastIndex && !state.isLoading) {
                onBottomReached()
            }
            key(images[i].id) {
                ImagesListItem(image = images[i], onItemClick)
                Divider(color = Color.Gray)
            }
        }
        item {
            if (state.isLoading) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.margin_medium)),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewImagesList() {
    ImagesList(
        ImagesListState(
            query = "",
            images = listOf(
                ImageEntity(
                    id = 0,
                    thumbnailUrl = "",
                    tags = "many, cool, tags",
                    userName = "A username",
                    fullImageUrl = "",
                    numLikes = 0,
                    numComments = 0,
                    numDownloads = 0
                ),
                ImageEntity(
                    id = 0,
                    thumbnailUrl = "",
                    tags = "many, cool, tags",
                    userName = "A username",
                    fullImageUrl = "",
                    numLikes = 0,
                    numComments = 0,
                    numDownloads = 0
                ),
                ImageEntity(
                    id = 0,
                    thumbnailUrl = "",
                    tags = "many, cool, tags",
                    userName = "A username",
                    fullImageUrl = "",
                    numLikes = 0,
                    numComments = 0,
                    numDownloads = 0
                )
            )
        ),
        onBottomReached = {},
        onItemClick = {},
    )
}