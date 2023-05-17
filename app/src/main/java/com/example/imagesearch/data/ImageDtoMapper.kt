package com.example.imagesearch.data

import com.example.imagesearch.app.checkNotEmpty
import com.example.imagesearch.data.dto.ImageDto
import com.example.imagesearch.domain.model.Image
import javax.inject.Inject

class ImageDtoMapper @Inject constructor() {

    fun transform(imageDto: ImageDto): Image? {

        return try {
            checkNotNull(imageDto.id)

            checkNotNull(imageDto.webformatURL)
            checkNotEmpty(imageDto.webformatURL)

            checkNotNull(imageDto.user)
            checkNotEmpty(imageDto.user)

            checkNotNull(imageDto.tags)
            checkNotEmpty(imageDto.tags)

            Image(
                id = imageDto.id,
                thumbnailUrl = imageDto.webformatURL,
                userName = imageDto.user,
                tags = imageDto.tags ?: ""
            )
        } catch (e: IllegalStateException) {
            null
        }
    }

}