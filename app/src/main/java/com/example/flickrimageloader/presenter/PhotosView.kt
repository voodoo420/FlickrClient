package com.example.flickrimageloader.presenter

import com.example.flickrimageloader.models.Photo

interface PhotosView {
    fun showProgress()
    fun hideProgress()
    fun showPhotos(photos: List<Photo>)
    fun showMessage(msg: String?)
}