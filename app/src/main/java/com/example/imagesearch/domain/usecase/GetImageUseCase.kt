package com.example.imagesearch.domain.usecase

import com.example.imagesearch.domain.model.Image
import com.example.imagesearch.domain.model.NetworkResult
import com.example.imagesearch.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetImageUseCase @Inject constructor(private val imageRepository: ImageRepository) :
    UseCase<Image> {

    data class Params(val id: Int) : UseCaseParams

    override fun build(params: UseCaseParams?): Flow<NetworkResult<Image>> {
        return params?.let {
            it as Params
            imageRepository.getImage(it.id)
        } ?: flowOf(NetworkResult.Error(Throwable("missing id")))
    }

}