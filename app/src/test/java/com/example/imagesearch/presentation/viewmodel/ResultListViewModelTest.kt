package com.example.imagesearch.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.imagesearch.TestDispatcherProvider
import com.example.imagesearch.domain.model.Image
import com.example.imagesearch.domain.model.NetworkResult
import com.example.imagesearch.domain.usecase.GetImagesUseCase
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
class ResultListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var testDispatcherProvider: DispatcherProvider
    private lateinit var getImagesUseCase: GetImagesUseCase
    private lateinit var imageEntityMapper: ImageEntityMapper
    private lateinit var viewModel: ResultListViewModel

    @Before
    fun setup() {
        getImagesUseCase = mockk()
        imageEntityMapper = ImageEntityMapper()
        testDispatcherProvider = TestDispatcherProvider()
        viewModel = ResultListViewModel(getImagesUseCase, imageEntityMapper, testDispatcherProvider)
    }

    @Test
    fun `getImages with valid query should update resultListLiveEvent with ImageEntities`() =
        runTest(testDispatcherProvider.io()) {
            val query = "cats"
            val images = listOf(
                Image(1, "", "", "", "", 0, 0, 0),
                Image(2, "", "", "", "", 0, 0, 0)
            )
            val expectedEntities = images.map {
                ImageEntity(it.id, it.thumbnailUrl, it.tags, it.userName, it.fullImageUrl, 0, 0, 0)
            }
            val observer: Observer<List<ImageEntity>> = mockk(relaxed = true)
            viewModel.resultListLiveEvent.observeForever(observer)
            coEvery { getImagesUseCase.build(any()) } returns flowOf(NetworkResult.Success(images))

            viewModel.getImages(query)

            verify { observer.onChanged(expectedEntities) }
            val result = viewModel.resultListLiveEvent.value
            result?.size shouldBeEqualTo expectedEntities.size
            result?.first()?.id shouldBeEqualTo 1
            result?.last()?.id shouldBeEqualTo 2
        }

    @Test
    fun `getImages with error result should not update resultListLiveEvent`() =
        runTest(testDispatcherProvider.io()) {
            val query = "cats"
            val error = Throwable("Some error")
            val observer: Observer<List<ImageEntity>> = mockk(relaxed = true)
            viewModel.resultListLiveEvent.observeForever(observer)
            coEvery { getImagesUseCase.build(any()) } returns flowOf(NetworkResult.Error(error))

            viewModel.getImages(query)

            verify(exactly = 0) { observer.onChanged(any()) }
        }

}
