package com.example.imagesearch.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.R
import com.example.imagesearch.app.LOG_TAG
import com.example.imagesearch.presentation.viewmodel.ResultsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsFragment : Fragment(R.layout.fragment_results) {

    private val resultsViewModel: ResultsViewModel by viewModels()

    private lateinit var toolbar: Toolbar
    private lateinit var list: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            toolbar = findViewById(R.id.resultsFragmentToolbar)

            val imageListAdapter = ImageListAdapter()
            list = findViewById<RecyclerView>(R.id.imagesList).apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = imageListAdapter
            }

            setupToolbar()

            resultsViewModel.resultsLiveEvent.observe(viewLifecycleOwner) {
                Log.d(LOG_TAG, "results fetched: ${it.size}")
                imageListAdapter.submitList(it)
            }
        }
    }

    private fun setupToolbar() {
        val searchViewMenuItem = toolbar.menu.findItem(R.id.action_results_fragment_search)
        val searchView = searchViewMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    resultsViewModel.getImages(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?) = true
        })
    }

}