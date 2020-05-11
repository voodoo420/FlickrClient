package com.example.flickrimageloader.presenter

interface PhotosPresenter {
    fun onStart()
    fun onStop()
    fun search(query: String)
}