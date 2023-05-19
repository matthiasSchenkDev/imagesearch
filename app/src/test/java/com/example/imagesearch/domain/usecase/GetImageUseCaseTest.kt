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
class GetImageUseCaseTest {

    private lateinit var imageRepository: ImageRepository
    private lateinit var getImageUseCase: GetImageUseCase

    @Before
    fun setup() {
        imageRepository = mockk()
        getImageUseCase = GetImageUseCase(imageRepository)
    }

    @Test
    fun `build with valid id should return image`() = runTest {
        val id = 123
        val expectedImage = Image(id, "", "", "", "", 0, 0, 0)
        coEvery { imageRepository.getImage(id) } returns flowOf(NetworkResult.Success(expectedImage))

        getImageUseCase.build(GetImageUseCase.Params(id)).collect {
            it `should be instance of` NetworkResult.Success::class.java
            it as NetworkResult.Success
            it.data.id shouldBeEqualTo id
        }

        coVerify { imageRepository.getImage(any()) }
    }

    @Test
    fun `build with missing id should return error`() = runTest {
        getImageUseCase.build(null).collect {
            it `should be instance of` NetworkResult.Error::class.java
            it as NetworkResult.Error
            it.e.message shouldBeEqualTo "missing id"
        }
    }

}
