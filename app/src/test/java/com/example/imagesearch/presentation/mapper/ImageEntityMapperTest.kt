package com.example.imagesearch.presentation.mapper

import com.example.imagesearch.domain.model.Image
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class ImageEntityMapperTest {

    private lateinit var imageEntityMapper: ImageEntityMapper

    @Before
    fun setup() {
        imageEntityMapper = ImageEntityMapper()
    }

    @Test
    fun `transform should map Image to ImageEntity correctly`() {
        val image = Image(
            id = 1,
            thumbnailUrl = "thumbnailUrl",
            tags = "tags",
            userName = "userName",
            fullImageUrl = "fullImageUrl",
            numLikes = 10,
            numComments = 5,
            numDownloads = 20
        )

        val result = imageEntityMapper.transform(image)

        result.id shouldBeEqualTo image.id
        result.thumbnailUrl shouldBeEqualTo image.thumbnailUrl
        result.tags shouldBeEqualTo image.tags
        result.userName shouldBeEqualTo image.userName
        result.fullImageUrl shouldBeEqualTo image.fullImageUrl
        result.numLikes shouldBeEqualTo image.numLikes
        result.numComments shouldBeEqualTo image.numComments
        result.numDownloads shouldBeEqualTo image.numDownloads
    }

}
