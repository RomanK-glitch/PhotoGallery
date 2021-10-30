package com.roman.photogallery.gallery

import android.app.Application
import androidx.lifecycle.*
import com.roman.photogallery.DaggerAppComponent
import com.roman.photogallery.FlickrFetchr
import com.roman.photogallery.GalleryItem
import com.roman.photogallery.QueryPreferences

class PhotoGalleryViewModel(private val app: Application) : AndroidViewModel(app) {

    val galleryItemLiveData: LiveData<List<GalleryItem>>

    private val flickrFetchr : FlickrFetchr
    private val mutableSearchTerm = MutableLiveData<String>()

    val searchTerm: String
        get() = mutableSearchTerm.value ?: ""

    init {
        mutableSearchTerm.value = QueryPreferences.getStoredQuery(app)
        flickrFetchr = DaggerAppComponent.create().flickrFetchr

        galleryItemLiveData = Transformations.switchMap(mutableSearchTerm) { searchTerm ->
            if (searchTerm.isBlank()) {
                flickrFetchr.fetchPhotos()
            } else {
                flickrFetchr.searchPhotos(searchTerm)
            }
        }
    }

    fun fetchPhotos(query: String = "") {
        QueryPreferences.setStoredQuery(app, query)
        mutableSearchTerm.value = query
    }
}