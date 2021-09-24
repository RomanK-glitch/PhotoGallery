package com.roman.photogallery.api

import com.google.gson.annotations.SerializedName
import com.roman.photogallery.GalleryItem

class PhotoResponse {
    @SerializedName("photo")
    lateinit var galleryItems: List<GalleryItem>
}