package com.example.imagesearch.presentation

import com.example.imagesearch.domain.model.Image
import javax.inject.Inject

class ImageEntityMapper @Inject constructor() {

    fun transform(image: Image): ImageEntity {
        return ImageEntity(
            id = image.id,
            thumbnailUrl = image.thumbnailUrl,
            userName = image.userName,
            tags = image.tags
        )
    }

}