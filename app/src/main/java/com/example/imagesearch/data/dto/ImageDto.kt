package com.example.imagesearch.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageDto(
    val collections: Int?,
    val comments: Int?,
    val downloads: Int?,
    @PrimaryKey val id: Int?,
    @ColumnInfo(name = "image_height") val imageHeight: Int?,
    @ColumnInfo(name = "image_size") val imageSize: Int?,
    @ColumnInfo(name = "image_width") val imageWidth: Int?,
    @ColumnInfo(name = "large_image_url") val largeImageURL: String?,
    val likes: Int?,
    @ColumnInfo(name = "page_url") val pageURL: String?,
    @ColumnInfo(name = "preview_height") val previewHeight: Int?,
    @ColumnInfo(name = "preview_url") val previewURL: String?,
    @ColumnInfo(name = "preview_width")
    val previewWidth: Int?,
    val tags: String?,
    val type: String?,
    val user: String?,
    @ColumnInfo(name = "user_image_url") val userImageURL: String?,
    val user_id: Int?,
    val views: Int?,
    @ColumnInfo(name = "web_format_height") val webformatHeight: Int?,
    @ColumnInfo(name = "web_format_url") val webformatURL: String?,
    @ColumnInfo(name = "web_format_width") val webformatWidth: Int?
)