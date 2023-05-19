package com.example.imagesearch.domain.usecase

import com.example.imagesearch.domain.model.Image
import com.example.imagesearch.domain.model.NetworkResult
import com.example.imagesearch.domain.repository.ImageRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetImagesUseCaseTest {
    private lateinit var imageRepository: ImageRepository
    private lateinit var getImagesUseCase: GetImagesUseCase

    @Before
    fun setup() {
        imageRepository = mockk()
        getImagesUseCase = GetImagesUseCase(imageRepository)
    }

    @Test
    fun `build with valid query should return images`() = runTest {
        val query = "cats"
        val expectedImages = listOf(
            Image(1, "", "", "", "", 0, 0, 0),
            Image(2, "", "", "", "", 0, 0, 0)
        )
        coEvery { imageRepository.getImagesPaginated(query) } returns flowOf(
            NetworkResult.Success(
                expectedImages
            )
        )

        getImagesUseCase.build(GetImagesUseCase.Params(query)).collect {
            it `should be instance of` NetworkResult.Success::class.java
            it as NetworkResult.Success
            it.data.size shouldBeEqualTo 2
            it.data.first().id shouldBeEqualTo 1
            it.data.last().id shouldBeEqualTo 2
        }

        coVerify { imageRepository.getImagesPaginated(any()) }
    }

    @Test
    fun `build with missing query should return error`() = runTest {
        getImagesUseCase.build(null).collect {
            it `should be instance of` NetworkResult.Error::class.java
            it as NetworkResult.Error
            it.e.message shouldBeEqualTo "missing query"
        }
    }

}
