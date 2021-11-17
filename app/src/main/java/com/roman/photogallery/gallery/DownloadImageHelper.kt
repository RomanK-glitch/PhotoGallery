package com.roman.photogallery.gallery

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

class DownloadImageHelper(private val downloadService: DownloadService) : Target {
    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        downloadService.stopForeground(true)
    }

    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        downloadService.stopForeground(true)
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        TODO("Not yet implemented")
    }
}