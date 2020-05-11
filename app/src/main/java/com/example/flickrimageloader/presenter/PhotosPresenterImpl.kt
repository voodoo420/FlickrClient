package com.example.flickrimageloader.presenter

import com.example.flickrimageloader.data.PhotosRemoteDataSource
import com.example.flickrimageloader.models.Photo
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import timber.log.Timber

class PhotosPresenterImpl(private val dataSource: PhotosRemoteDataSource, private val view: PhotosView) : PhotosPresenter{

    private var disposable: Disposable? = null

    override fun onStart() {
        view.showProgress()
        dataSource.getRecent().subscribe(PhotosObserver())
    }

    override fun onStop() {
        disposable?.dispose()
    }

    override fun search(query: String) {
        dataSource.getPhotosByRequest(query).subscribe(PhotosObserver())
    }

    inner class PhotosObserver : SingleObserver<List<Photo>> {
        override fun onSuccess(t: List<Photo>) {
            view.hideProgress()
            view.showPhotos(t)
        }

        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onError(e: Throwable) {
            view.hideProgress()
            Timber.e(e.localizedMessage)
            view.showMessage("something goes wrong")
        }
    }

}