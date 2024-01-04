package com.example.imagesearch.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.imagesearch.R
import com.example.imagesearch.app.hide
import com.example.imagesearch.app.show
import com.example.imagesearch.presentation.viewmodel.DetailsViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment(val a: ViewModel) : Fragment(R.layout.fragment_details) {

    private val detailsViewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    private lateinit var toolbar: Toolbar
    private lateinit var image: ImageView
    private lateinit var loadingSpinner: ProgressBar
    private lateinit var errorView: View
    private lateinit var imageErrorView: View
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
            image = findViewById(R.id.image)
            loadingSpinner = findViewById(R.id.loadingSpinner)
            errorView = findViewById(R.id.errorView)
            imageErrorView = findViewById(R.id.imageErrorView)
            name = findViewById(R.id.name)
            tags = findViewById(R.id.tags)
            likes = findViewById(R.id.likes)
            comments = findViewById(R.id.comments)
            downloads = findViewById(R.id.downloads)
        }

        detailsViewModel.imageResultLiveEvent.observe(viewLifecycleOwner) { image ->
            image?.let {
                Picasso
                    .get()
                    .load(image.fullImageUrl)
                    .error(R.drawable.ic_camera)
                    .into(this.image, object : Callback {
                        override fun onSuccess() {
                            loadingSpinner.hide()
                        }

                        override fun onError(e: Exception?) {
                            loadingSpinner.hide()
                            imageErrorView.show()
                        }
                    })
                name.text = it.userName
                tags.text = it.tags
                likes.text = it.numLikes.toString()
                comments.text = it.numComments.toString()
                downloads.text = it.numDownloads.toString()
            } ?: errorView.show()
        }

        detailsViewModel.getImage(args.imageId)
    }

}