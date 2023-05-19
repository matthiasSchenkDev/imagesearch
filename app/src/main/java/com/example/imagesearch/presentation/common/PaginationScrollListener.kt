package com.example.imagesearch.presentation.common

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener

class PaginationScrollListener(private val onScrolledToBottom: () -> Unit) : OnScrollListener() {

    companion object {
        private const val SCROLL_DIRECTION_DOWN = -1
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val isScrolledToBottom =
            recyclerView.canScrollVertically(SCROLL_DIRECTION_DOWN) && newState == RecyclerView.SCROLL_STATE_IDLE
        if (isScrolledToBottom) {
            onScrolledToBottom()
        }
    }

}