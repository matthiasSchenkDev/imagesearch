package com.example.imagesearch.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.imagesearch.TestDispatcherProvider
import com.example.imagesearch.domain.model.Image
import com.example.imagesearch.domain.model.NetworkResult
import com.example.imagesearch.domain.usecase.GetImageUseCase
import com.example.imagesearch.presentation.common.DispatcherProvider
import com.example.imagesearch.presentation.mapper.ImageEntityMapper
import com.example.imagesearch.presentation.model.ImageEntity
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var testDispatcherProvider: DispatcherProvider
    private lateinit var getImageUseCase: GetImageUseCase
    private lateinit var imageEntityMapper: ImageEntityMapper
    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup() {
        getImageUseCase = mockk()
        imageEntityMapper = mockk()
        testDispatcherProvider = TestDispatcherProvider()
        viewModel = DetailsViewModel(getImageUseCase, imageEntityMapper, testDispatcherProvider)
    }

    @Test
    fun `getImage with valid id should update imageResultLiveEvent with ImageEntity`() =
        runTest(testDispatcherProvider.io()) {
            val id = 123
            val image = Image(id, "", "", "", "", 0, 0, 0)
            val expectedEntity = ImageEntity(id, "", "", "", "", 0, 0, 0)
            coEvery { getImageUseCase.build(any()) } returns flowOf(NetworkResult.Success(image))
            coEvery { imageEntityMapper.transform(image) } returns expectedEntity
            val observer: Observer<ImageEntity?> = mockk(relaxed = true)
            viewModel.imageResultLiveEvent.observeForever(observer)

            viewModel.getImage(id)

            verify { observer.onChanged(expectedEntity) }
            viewModel.imageResultLiveEvent.value?.id shouldBeEqualTo expectedEntity.id
        }

    @Test
    fun `getImage with error result should update imageResultLiveEvent with null`() =
        runTest(testDispatcherProvider.io()) {
            val id = 123
            val error = Throwable("Some error")
            coEvery { getImageUseCase.build(any()) } returns flowOf(NetworkResult.Error(error))
            val observer: Observer<ImageEntity?> = mockk(relaxed = true)
            viewModel.imageResultLiveEvent.observeForever(observer)
            viewModel.getImage(id)

            verify { observer.onChanged(null) }
            viewModel.imageResultLiveEvent.value shouldBeEqualTo null
        }

}
