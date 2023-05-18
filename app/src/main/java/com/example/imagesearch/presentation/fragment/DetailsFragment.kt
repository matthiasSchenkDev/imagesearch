package com.example.imagesearch.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.imagesearch.R

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var toolbar: Toolbar
    private lateinit var name: TextView
    private lateinit var tags: TextView
    private lateinit var likes: TextView
    private lateinit var comments: TextView
    private lateinit var downloads: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            toolbar = findViewById(R.id.detailsFragmentToolbar)
            toolbar.navigationIcon =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_arrow_back, null)
            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
            name = findViewById(R.id.name)
            tags = findViewById(R.id.tags)
            likes = findViewById(R.id.likes)
            comments = findViewById(R.id.comments)
            downloads = findViewById(R.id.downloads)
        }
    }

}