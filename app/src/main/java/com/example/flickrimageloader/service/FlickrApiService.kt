package com.example.flickrimageloader.service

import com.example.flickrimageloader.BuildConfig
import com.example.flickrimageloader.models.ResultSuccess
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {
    @GET("services/rest")
    fun getRecentPhotos(
        @Query("method") method: String,
        @Query("api_key") api_key: String,
        @Query("per_page") per_page: Int,
        @Query("format") format: String,
        @Query("nojsoncallback") nojsoncallback: Int
    ): Single<ResultSuccess>

    @GET("services/rest")
    fun getPhotos(
        @Query("method") method: String,
        @Query("api_key") api_key: String,
        @Query("text") text: String,
        @Query("per_page") per_page: Int,
        @Query("format") format: String,
        @Query("nojsoncallback") nojsoncallback: Int
    ): Single<ResultSuccess>

    companion object Factory{
        fun create(): FlickrApiService {
            val interceptor = HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            )

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://www.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(FlickrApiService::class.java)
        }
    }
}