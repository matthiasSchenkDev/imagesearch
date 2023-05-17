package com.example.imagesearch.presentation

import com.example.imagesearch.domain.model.Image
import javax.inject.Inject

class ImageEntityMapper @Inject constructor() {

    companion object {
        private const val THUMBNAIL_IMAGE_HEIGHT = 180
    }

    fun transform(image: Image): ImageEntity {
        val heightMatcher = Regex("_\\d*.")
        val url = image.thumbnailUrl.replace(heightMatcher, "_${THUMBNAIL_IMAGE_HEIGHT}.")

        return ImageEntity(
            id = image.id,
            thumbnailUrlResized = url,
            userName = image.userName,
            tags = image.tags
        )
    }

}