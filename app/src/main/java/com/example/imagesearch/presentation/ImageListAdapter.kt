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
    ListAdapter<ImageEntity, RecyclerView.ViewHolder>(ImageDiffCallBack()) {

    var onItemClickListener: ((image: ImageEntity) -> Unit)? = null

    companion object {
        private const val IMAGE_ITEM = 0
        private const val LOADING_ITEM = 1
    }

    private var isLoadingItemAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = when (viewType) {
            IMAGE_ITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_image, parent, false)
                ImageViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_loading, parent, false)
                LoadingViewHolder(view)
            }
        }
        return viewHolder
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == currentList.lastIndex && isLoadingItemAdded) {
            LOADING_ITEM
        } else IMAGE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            IMAGE_ITEM -> {
                val image = getItem(position) as ImageEntity
                with(holder as ImageViewHolder) {
                    Picasso
                        .get()
                        .load(image.thumbnailUrl)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(thumbnail)
                    name.text =
                        itemView.context.getString(R.string.list_item_user_name, image.userName)
                    tags.text = itemView.context.getString(R.string.list_item_tags, image.tags)
                }
                holder.itemView.setOnClickListener { onItemClickListener?.invoke(image) }
            }

            else -> {}
        }
    }

    fun addLoadingItem() {
        isLoadingItemAdded = true
        val list = currentList.toMutableList()
        list.add(ImageEntity(-1, "", "", ""))
        submitList(list)
    }

    fun removeLoadingItem() {
        val list = currentList.toMutableList()
        if (isLoadingItemAdded && list.isNotEmpty()) {
            isLoadingItemAdded = false
            list.removeAt(list.lastIndex)
            submitList(list)
        }
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
        val name: TextView = itemView.findViewById(R.id.name)
        val tags: TextView = itemView.findViewById(R.id.tags)
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private class ImageDiffCallBack : DiffUtil.ItemCallback<ImageEntity>() {

        override fun areItemsTheSame(oldItem: ImageEntity, newItem: ImageEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ImageEntity, newItem: ImageEntity): Boolean =
            oldItem.thumbnailUrl == newItem.thumbnailUrl
                    && oldItem.userName == newItem.userName
                    && oldItem.tags == newItem.tags
    }

}