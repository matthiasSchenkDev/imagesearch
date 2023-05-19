package com.example.imagesearch.presentation.mapper

import com.example.imagesearch.domain.model.Image
import com.example.imagesearch.presentation.model.ImageEntity
import javax.inject.Inject

class ImageEntityMapper @Inject constructor() {

    fun transform(image: Image): ImageEntity {
        return ImageEntity(
            id = image.id,
            thumbnailUrl = image.thumbnailUrl,
            userName = image.userName,
            tags = image.tags,
            fullImageUrl = image.fullImageUrl,
            numLikes = image.numLikes,
            numComments = image.numComments,
            numDownloads = image.numDownloads,
        )
    }

}