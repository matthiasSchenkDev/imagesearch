package com.example.imagesearch.domain.usecase

import com.example.imagesearch.domain.model.Image
import com.example.imagesearch.domain.model.NetworkResult
import com.example.imagesearch.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) : UseCase<List<Image>> {

    data class Params(val query: String) : UseCaseParams

    override suspend fun build(params: UseCaseParams?): Flow<NetworkResult<List<Image>>> {
        return params?.let {
            it as Params
            imageRepository.getImagesPaginated(it.query)
        } ?: flowOf(NetworkResult.Error(Throwable("missing query")))
    }

}