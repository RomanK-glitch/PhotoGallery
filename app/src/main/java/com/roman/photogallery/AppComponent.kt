package com.roman.photogallery

import com.roman.photogallery.api.FlickrApi
import com.roman.photogallery.api.PhotoInterceptor
import com.roman.photogallery.gallery.PhotoGalleryViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(photoGalleryViewModel: PhotoGalleryViewModel)

    val flickrFetchr: FlickrFetchr
}

@Module
object AppModule {

    @Provides
    fun provideRetrofit (client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideClient(interceptor: PhotoInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    fun provideInterceptor() : PhotoInterceptor {
        return PhotoInterceptor()
    }

    @Provides
    fun provideApi(retrofit: Retrofit) : FlickrApi {
        return retrofit.create(FlickrApi::class.java)
    }

    @Provides
    fun provideFlickrFetchr(flickrApi: FlickrApi) : FlickrFetchr {
        return FlickrFetchr(flickrApi)
    }
}
