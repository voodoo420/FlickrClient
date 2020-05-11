package com.example.flickrimageloader.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrimageloader.R
import com.example.flickrimageloader.models.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotosRVAdapter : RecyclerView.Adapter<PhotosRVAdapter.ViewHolder>() {

    var photos: List<Photo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false))

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(photos[position])

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{
        fun bind(photo: Photo) = with(photo){
            Picasso.get()
                .load("https://farm${farm}.static.flickr.com/${server}/${id}_${secret}_q.jpg")
                .placeholder(R.drawable.ic_crop_original)
                .error(R.drawable.ic_block)
                .into(containerView.iv_photo)
        }
    }

}
