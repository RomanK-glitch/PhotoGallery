package com.roman.photogallery

import android.content.Context

val Context.appComponent : AppComponent
    get() = when (this) {
        is PhotoGalleryApplication -> appComponent
        else -> this.applicationContext.appComponent
    }
