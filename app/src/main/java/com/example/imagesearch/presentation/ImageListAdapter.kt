package com.example.imagesearch.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.R
import com.squareup.picasso.Picasso

class ImageListAdapter :
    ListAdapter<ImageEntity, ImageListAdapter.ImageViewHolder>(ImageDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = getItem(position) as ImageEntity
        with(holder) {
            Picasso
                .get()
                .load(image.thumbnailUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(thumbnail)
            name.text = itemView.context.getString(R.string.list_item_user_name, image.userName)
            tags.text = itemView.context.getString(R.string.list_item_tags, image.tags)
        }
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
        val name: TextView = itemView.findViewById(R.id.name)
        val tags: TextView = itemView.findViewById(R.id.tags)
    }

    private class ImageDiffCallBack : DiffUtil.ItemCallback<ImageEntity>() {

        override fun areItemsTheSame(oldItem: ImageEntity, newItem: ImageEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ImageEntity, newItem: ImageEntity): Boolean =
            oldItem.thumbnailUrl == newItem.thumbnailUrl
                    && oldItem.userName == newItem.userName
                    && oldItem.tags == newItem.tags
    }

}