package com.example.imagesearch.data.mapper

import com.example.imagesearch.data.dto.ImageDto
import com.example.imagesearch.domain.model.Image
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.Before
import org.junit.Test

class ImageDtoMapperTest {

    @MockK
    private lateinit var imageDto: ImageDto

    private lateinit var imageDtoMapper: ImageDtoMapper

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        imageDtoMapper = ImageDtoMapper()
    }

    @Test
    fun `transform should return valid Image object`() {
        val expectedImage = Image(
            id = 1,
            thumbnailUrl = "https://example.com/thumbnail.jpg",
            userName = "John Doe",
            tags = "nature, landscape",
            fullImageUrl = "https://example.com/image.jpg",
            numLikes = 10,
            numComments = 5,
            numDownloads = 20
        )

        every { imageDto.id } returns 1
        every { imageDto.previewURL } returns "https://example.com/thumbnail.jpg"
        every { imageDto.user } returns "John Doe"
        every { imageDto.tags } returns "nature, landscape"
        every { imageDto.largeImageURL } returns "https://example.com/image.jpg"
        every { imageDto.likes } returns 10
        every { imageDto.comments } returns 5
        every { imageDto.downloads } returns 20

        val result = imageDtoMapper.transform(imageDto)

        result shouldBeEqualTo expectedImage
    }

    @Test
    fun `transform should return null when ImageDto properties are null`() {
        every { imageDto.id } returns null
        every { imageDto.previewURL } returns null
        every { imageDto.user } returns null
        every { imageDto.tags } returns null
        every { imageDto.largeImageURL } returns null
        every { imageDto.likes } returns null
        every { imageDto.comments } returns null
        every { imageDto.downloads } returns null

        val result = imageDtoMapper.transform(imageDto)

        result.shouldBeNull()
    }

    @Test
    fun `transform should return null when ImageDto previewURL, user or tags are empty`() {
        every { imageDto.id } returns 1
        every { imageDto.previewURL } returns ""
        every { imageDto.user } returns ""
        every { imageDto.tags } returns ""
        every { imageDto.largeImageURL } returns "https://example.com/image.jpg"
        every { imageDto.likes } returns 10
        every { imageDto.comments } returns 5
        every { imageDto.downloads } returns 20

        val result = imageDtoMapper.transform(imageDto)

        result.shouldBeNull()
    }
    
}
