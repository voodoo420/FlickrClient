package com.example.flickrimageloader.data

import com.example.flickrimageloader.models.Photo
import io.reactivex.Single

interface PhotosDataSource {
    fun getRecent(): Single<List<Photo>>
    fun getPhotosByRequest(queryText: String): Single<List<Photo>>
}