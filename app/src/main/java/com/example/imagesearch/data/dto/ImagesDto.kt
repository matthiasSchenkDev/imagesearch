package com.example.imagesearch.data.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ImagesDto(
    @JsonProperty("hits") val hits: List<ImageDto>?,
    @JsonProperty("total") val total: Int?,
    @JsonProperty("totalHits") val totalHits: Int?
)