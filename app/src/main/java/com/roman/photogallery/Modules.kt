package com.roman.photogallery

import com.roman.photogallery.api.FlickrApi
import com.roman.photogallery.api.PhotoInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { provideRetrofit(get()) }

    single { provideHttpClient(get()) }

    single { provideInterceptor() }

    single { provideApi(get()) }

    single { provideFlickrFetchr(get()) }
}

fun provideRetrofit(client: OkHttpClient) : Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.flickr.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}

fun provideHttpClient(interceptor: PhotoInterceptor) : OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
}

fun provideInterceptor() : PhotoInterceptor {
    return PhotoInterceptor()
}

fun provideApi(retrofit: Retrofit) : FlickrApi {
    return retrofit.create(FlickrApi::class.java)
}

fun provideFlickrFetchr(flickrApi: FlickrApi): FlickrFetchr {
    return FlickrFetchr(flickrApi)
}
