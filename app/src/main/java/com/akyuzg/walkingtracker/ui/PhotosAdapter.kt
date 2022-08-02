package com.akyuzg.walkingtracker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.akyuzg.walkingtracker.databinding.PhotoItemBinding
import com.akyuzg.walkingtracker.ui.model.PhotoItemView
import com.akyuzg.walkingtracker.ui.model.toUrl
import com.bumptech.glide.Glide

class PhotoAdapter(private val photos: MutableList<PhotoItemView>) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var photoImageView: ImageView = binding.photoImageView
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PhotoViewHolder {
        val binding = PhotoItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = photos[position]

        Glide.with(holder.photoImageView)
            .load(photo.toUrl())
            .into(holder.photoImageView);
    }

    fun setItems(photoItemViews: List<PhotoItemView>) {
        photos.clear()
        photos.addAll(photoItemViews)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = photos.size
}
