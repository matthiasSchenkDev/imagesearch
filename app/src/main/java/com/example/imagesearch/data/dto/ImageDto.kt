package com.example.imagesearch.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ImageDto(
    @SerializedName("collections")
    val collections: Int?,
    @SerializedName("comments")
    val comments: Int?,
    @SerializedName("downloads")
    val downloads: Int?,
    @PrimaryKey
    @SerializedName("id")
    val id: Int?,
    @ColumnInfo(name = "image_height")
    @SerializedName("imageHeight")
    val imageHeight: Int?,
    @ColumnInfo(name = "image_size")
    @SerializedName("imageSize")
    val imageSize: Int?,
    @ColumnInfo(name = "image_width")
    @SerializedName("imageWidth")
    val imageWidth: Int?,
    @ColumnInfo(name = "large_image_url")
    @SerializedName("largeImageURL")
    val largeImageURL: String?,
    @SerializedName("likes")
    val likes: Int?,
    @ColumnInfo(name = "page_url")
    @SerializedName("pageURL")
    val pageURL: String?,
    @ColumnInfo(name = "preview_height")
    @SerializedName("previewHeight")
    val previewHeight: Int?,
    @ColumnInfo(name = "preview_url")
    @SerializedName("previewURL")
    val previewURL: String?,
    @ColumnInfo(name = "preview_width")
    @SerializedName("previewWidth")
    val previewWidth: Int?,
    @SerializedName("tags")
    val tags: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("user")
    val user: String?,
    @ColumnInfo(name = "user_image_url")
    @SerializedName("userImageURL")
    val userImageURL: String?,
    @SerializedName("user_id")
    val user_id: Int?,
    @SerializedName("views")
    val views: Int?,
    @ColumnInfo(name = "web_format_height")
    @SerializedName("webformatHeight")
    val webformatHeight: Int?,
    @ColumnInfo(name = "web_format_url")
    @SerializedName("webformatURL")
    val webformatURL: String?,
    @ColumnInfo(name = "web_format_width")
    @SerializedName("webformatWidth")
    val webformatWidth: Int?
)