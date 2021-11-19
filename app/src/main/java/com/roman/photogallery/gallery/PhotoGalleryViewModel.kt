package com.roman.photogallery.gallery

import android.app.Application
import androidx.lifecycle.*
import com.roman.photogallery.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import javax.inject.Inject

class PhotoGalleryViewModel(private val app: Application) : AndroidViewModel(app), KoinComponent {

    val galleryItemLiveData: LiveData<List<GalleryItem>>

    //by dagger
//    @Inject
//    lateinit var flickrFetchr : FlickrFetchr

    //by koin
    val flickrFetchr : FlickrFetchr by inject()

    private val mutableSearchTerm = MutableLiveData<String>()

    val searchTerm: String
        get() = mutableSearchTerm.value ?: ""

    init {
        mutableSearchTerm.value = QueryPreferences.getStoredQuery(app)
        val application = getApplication() as PhotoGalleryApplication
        application.appComponent.inject(this)

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