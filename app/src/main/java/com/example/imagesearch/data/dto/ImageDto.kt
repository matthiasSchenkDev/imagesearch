package com.example.imagesearch.data.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ImageDto(
    @JsonProperty("collections") val collections: Int?,
    @JsonProperty("comments") val comments: Int?,
    @JsonProperty("downloads") val downloads: Int?,
    @JsonProperty("id") val id: Int?,
    @JsonProperty("imageHeight") val imageHeight: Int?,
    @JsonProperty("imageSize") val imageSize: Int?,
    @JsonProperty("imageWidth") val imageWidth: Int?,
    @JsonProperty("largeImageURL") val largeImageURL: String?,
    @JsonProperty("likes") val likes: Int?,
    @JsonProperty("pageURL") val pageURL: String?,
    @JsonProperty("previewHeight") val previewHeight: Int?,
    @JsonProperty("previewURL") val previewURL: String?,
    @JsonProperty("previewWidth") val previewWidth: Int?,
    @JsonProperty("tags") val tags: String?,
    @JsonProperty("type") val type: String?,
    @JsonProperty("user") val user: String?,
    @JsonProperty("userImageURL") val userImageURL: String?,
    @JsonProperty("user_id") val user_id: Int?,
    @JsonProperty("views") val views: Int?,
    @JsonProperty("webformatHeight") val webformatHeight: Int?,
    @JsonProperty("webformatURL") val webformatURL: String?,
    @JsonProperty("webformatWidth") val webformatWidth: Int?
)