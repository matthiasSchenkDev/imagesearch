package com.example.imagesearch.data.mapper

import com.example.imagesearch.app.checkNotNullOrEmpty
import com.example.imagesearch.data.dto.ImageDto
import com.example.imagesearch.domain.model.Image
import javax.inject.Inject

class ImageDtoMapper @Inject constructor() {

    fun transform(imageDto: ImageDto): Image? {
        return try {
            checkNotNull(imageDto.id)
            checkNotNullOrEmpty(imageDto.previewURL)
            checkNotNullOrEmpty(imageDto.user)
            checkNotNullOrEmpty(imageDto.tags)
            checkNotNull(imageDto.largeImageURL)
            checkNotNull(imageDto.likes)
            checkNotNull(imageDto.comments)
            checkNotNull(imageDto.downloads)

            Image(
                id = imageDto.id,
                thumbnailUrl = imageDto.previewURL!!,
                userName = imageDto.user!!,
                tags = imageDto.tags!!,
                fullImageUrl = imageDto.largeImageURL,
                numLikes = imageDto.likes,
                numComments = imageDto.comments,
                numDownloads = imageDto.downloads
            )
        } catch (e: IllegalStateException) {
            null
        }
    }

}