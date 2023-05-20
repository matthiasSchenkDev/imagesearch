package com.example.imagesearch.data.repo

import com.example.imagesearch.data.api.ImageApi
import com.example.imagesearch.data.db.ImageDao
import com.example.imagesearch.data.db.SearchDao
import com.example.imagesearch.data.dto.ImageDto
import com.example.imagesearch.data.dto.ImagesDto
import com.example.imagesearch.data.dto.SearchDto
import com.example.imagesearch.data.mapper.ImageDtoMapper
import com.example.imagesearch.domain.model.Image
import com.example.imagesearch.domain.model.NetworkResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class ImageRepositoryImplTest {

    private lateinit var imageRepositoryImpl: ImageRepositoryImpl
    private lateinit var imageApi: ImageApi
    private lateinit var imageDtoMapper: ImageDtoMapper
    private lateinit var imageDao: ImageDao
    private lateinit var searchDao: SearchDao

    @Before
    fun setup() {
        imageApi = mockk()
        imageDtoMapper = mockk()
        imageDao = mockk(relaxed = true)
        searchDao = mockk(relaxed = true)

        imageRepositoryImpl = ImageRepositoryImpl(
            imageApi = imageApi,
            imageDtoMapper = imageDtoMapper,
            imageDao = imageDao,
            searchDao = searchDao,
            apiKey = "test-api-key"
        )
    }

    @Test
    fun `getImagesPaginated should return success result when cache images are available`() =
        runTest {
            val query = "cats"
            val imageDtoList = listOf(mockk<ImageDto>())
            val images = listOf(mockk<Image>())

            coEvery { searchDao.getSearch(any(), any()) } returns SearchDto("", 1, imageDtoList)
            coEvery { imageApi.getImages(any(), any(), any()) } returns ImagesDto(null, null, null)
            coEvery { imageDtoMapper.transform(any()) } returnsMany images

            imageRepositoryImpl.getImagesPaginated(query).collect {
                it `should be instance of` NetworkResult.Success::class.java
                it as NetworkResult.Success
                it.value.size shouldBeEqualTo 1
            }

            coVerify(exactly = 0) { imageApi.getImages(any(), any(), any()) }
            coVerify(exactly = 1) { searchDao.getSearch(any(), any()) }
            coVerify(exactly = 1) { imageDtoMapper.transform(any()) }
        }

    @Test
    fun `getImagesPaginated should return success result when web images are available`() =
        runTest {
            val query = "cats"
            val imageDtoList = listOf(mockk<ImageDto>())
            val images = listOf(mockk<Image>())

            coEvery { searchDao.getSearch(any(), any()) } returns null
            coEvery { imageApi.getImages(any(), any(), any()) } returns ImagesDto(
                imageDtoList,
                null,
                null
            )
            coEvery { imageDtoMapper.transform(any()) } returnsMany images

            imageRepositoryImpl.getImagesPaginated(query).collect {
                it `should be instance of` NetworkResult.Success::class.java
                it as NetworkResult.Success
                it.value.size shouldBeEqualTo 1
            }

            coVerify(exactly = 1) { searchDao.getSearch(any(), any()) }
            coVerify(exactly = 1) {
                imageApi.getImages(
                    "test-api-key",
                    query,
                    ImageApi.DEFAULT_PAGE
                )
            }
            coVerify(exactly = 1) { searchDao.insertSearch(any()) }
            coVerify(exactly = 1) { imageDtoMapper.transform(any()) }
        }

    @Test
    fun `getImagesPaginated should return error result when no images available`() = runTest {
        val query = "dogs"
        val expectedNetworkResult =
            NetworkResult.Error<List<Image>>(Throwable("no images available for query: $query"))

        coEvery { searchDao.getSearch(any(), any()) } returns null
        coEvery { imageApi.getImages(any(), any(), any()) } returns ImagesDto(null, null, null)

        imageRepositoryImpl.getImagesPaginated(query).collect {
            it `should be instance of` NetworkResult.Error::class.java
            it as NetworkResult.Error
            it.value.message shouldBeEqualTo expectedNetworkResult.value.message
        }

        coVerify(exactly = 1) { imageApi.getImages("test-api-key", query, ImageApi.DEFAULT_PAGE) }
        coVerify(exactly = 0) { searchDao.insertSearch(any()) }
        coVerify(exactly = 0) { imageDtoMapper.transform(any()) }
    }

    @Test
    fun `getImage should return success result when a cache image is available`() = runTest {
        val id = 1
        val imageDto = mockk<ImageDto>()
        val image = Image(id, "", "", "", "", 0, 0, 0)

        coEvery { imageDao.getImage(any()) } returns imageDto
        coEvery { imageApi.getImage("test-api-key", any()) } returns ImagesDto(null, null, null)
        every { imageDtoMapper.transform(any()) } returns image

        imageRepositoryImpl.getImage(id).collect {
            it `should be instance of` NetworkResult.Success::class.java
            it as NetworkResult.Success
            it.value.id shouldBeEqualTo id
        }

        coVerify(exactly = 1) { imageDao.getImage(any()) }
        coVerify(exactly = 0) { imageApi.getImage(any(), any()) }
        coVerify(exactly = 0) { imageDao.insertImage(any()) }
        coVerify(exactly = 1) { imageDtoMapper.transform(any()) }
    }

    @Test
    fun `getImage should return success result when a web image is available`() = runTest {
        val id = 1
        val imageDto = mockk<ImageDto>()
        val image = Image(id, "", "", "", "", 0, 0, 0)

        coEvery { imageDao.getImage(any()) } returns null
        coEvery { imageApi.getImage("test-api-key", any()) } returns ImagesDto(
            listOf(imageDto),
            null,
            null
        )
        every { imageDtoMapper.transform(any()) } returns image

        imageRepositoryImpl.getImage(id).collect {
            it `should be instance of` NetworkResult.Success::class.java
            it as NetworkResult.Success
            it.value.id shouldBeEqualTo id
        }

        coVerify(exactly = 1) { imageDao.getImage(any()) }
        coVerify(exactly = 1) { imageApi.getImage("test-api-key", id.toString()) }
        coVerify(exactly = 1) { imageDao.insertImage(any()) }
        coVerify(exactly = 1) { imageDtoMapper.transform(any()) }
    }

    @Test
    fun `getImage should return error result when no image available`() = runTest {
        val id = 2
        val expectedNetworkResult =
            NetworkResult.Error<Image>(Throwable("no images available for id: $id"))

        coEvery { imageDao.getImage(any()) } returns null
        coEvery { imageApi.getImage(any(), any()) } returns ImagesDto(null, null, null)

        imageRepositoryImpl.getImage(id).collect {
            it `should be instance of` NetworkResult.Error::class.java
            it as NetworkResult.Error
            it.value.message shouldBeEqualTo expectedNetworkResult.value.message
        }

        coVerify(exactly = 1) { imageDao.getImage(any()) }
        coVerify(exactly = 1) { imageApi.getImage("test-api-key", id.toString()) }
        coVerify(exactly = 0) { imageDao.insertImage(any()) }
        coVerify(exactly = 0) { imageDtoMapper.transform(any()) }
    }

}
