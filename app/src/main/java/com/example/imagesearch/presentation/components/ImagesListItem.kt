package com.example.imagesearch.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.imagesearch.R
import com.example.imagesearch.presentation.model.ImageEntity

@Composable
fun ImagesListItem(
    image: ImageEntity,
    onClick: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.margin_medium))
            .clickable { onClick(image.id) }
    ) {
        AsyncImage(
            modifier = Modifier
                .size(size = dimensionResource(id = R.dimen.list_item_thumbnail_width))
                .background(Color.Gray),
            model = image.thumbnailUrl,
            contentDescription = image.thumbnailUrl,
            placeholder = painterResource(id = R.drawable.ic_camera),
        )
        Column(
            modifier = Modifier
                .padding(start = dimensionResource(id = R.dimen.margin_small)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.margin_small))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.margin_small))
            ) {
                Image(painter = painterResource(id = R.drawable.ic_person), contentDescription = "")
                Text(text = image.userName)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.margin_small))
            ) {
                Image(painter = painterResource(id = R.drawable.ic_label), contentDescription = "")
                Text(text = image.tags)
            }
        }
    }
}

@Preview
@Composable
fun PreviewImagesListItem() {
    ImagesListItem(
        image = ImageEntity(
            id = 0,
            thumbnailUrl = "",
            tags = "many, cool, tags",
            userName = "A username",
            fullImageUrl = "",
            numLikes = 0,
            numComments = 0,
            numDownloads = 0
        ), onClick = {}
    )
}