package com.example.flickrimageloader.activity

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.flickrimageloader.App
import com.example.flickrimageloader.R
import com.example.flickrimageloader.models.Photo
import com.example.flickrimageloader.adapter.PhotosRVAdapter
import com.example.flickrimageloader.di.ActivityModule
import com.example.flickrimageloader.presenter.PhotosPresenterImpl
import com.example.flickrimageloader.presenter.PhotosView
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), PhotosView {

    @Inject
    lateinit var presenter: PhotosPresenterImpl
    private lateinit var adapter: PhotosRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et_query.addTextChangedListener(getText())
        App.appComponent.createActivityComponent()
            .setModule(ActivityModule(this))
            .build()
            .inject(this)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    override fun onStart() {
        super.onStart()
        checkInternetConnection()
        presenter.onStart()
        //todo вычисление размера экрана
        hideKeypad(window.decorView)
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            rv_photos.layoutManager = GridLayoutManager(this, 6)
        } else rv_photos.layoutManager = GridLayoutManager(this, 4)
        adapter = PhotosRVAdapter()
        rv_photos.adapter = adapter
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val view = this.currentFocus
        view?.let { v ->
            hideKeypad(v)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun hideKeypad(parent: View?) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(parent?.windowToken, 0)
    }

    private fun checkInternetConnection(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo == null || !networkInfo.isConnected) {
            Toast.makeText(this, "internet needed", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun getText() = object : TextWatcher{
        private var timer = Timer()
        private val DELAY: Long = 500
        override fun afterTextChanged(s: Editable?) {
            val str = s.toString()
            if (checkInternetConnection()) {
                timer.cancel()
                timer = Timer()
                timer.schedule(
                    object : TimerTask() {
                        override fun run() {
                            if (s.toString().isNotEmpty()) {
                                presenter.search(s.toString())
                            }
                        }
                    }, DELAY
                )
            }
            Timber.d(str)
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showPhotos(photos: List<Photo>) {
        adapter.photos = photos
    }

    override fun showMessage(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        Timber.e(msg)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}
