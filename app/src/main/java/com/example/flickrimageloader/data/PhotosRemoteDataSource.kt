package com.example.flickrimageloader.data

import com.example.flickrimageloader.service.FlickrApiService
import com.example.flickrimageloader.models.ResultSuccess
import com.example.flickrimageloader.models.Photo

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PhotosRemoteDataSource(private val api: FlickrApiService) : PhotosDataSource {

    val METHOD_GETRECENT = "flickr.photos.getRecent"
    val METHOD_SEARCH = "flickr.photos.search"
    val API_KEY = "be5e5cec7d7f728b59e2f621143a6bce"
    val PER_PAGE = 160
    val FORMAT = "json"
    val NOJSONCALLBACK = 1

    //todo вывод ResultError во вью

    override fun getRecent(): Single<List<Photo>> {
        return api.getRecentPhotos(METHOD_GETRECENT, API_KEY, PER_PAGE, FORMAT, NOJSONCALLBACK)
            .map { t: ResultSuccess -> t.photosPage.photo  }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getPhotosByRequest(queryText: String): Single<List<Photo>> {
        return api.getPhotos(METHOD_SEARCH, API_KEY, queryText, PER_PAGE, FORMAT, NOJSONCALLBACK)
            .map { t: ResultSuccess -> t.photosPage.photo  }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
