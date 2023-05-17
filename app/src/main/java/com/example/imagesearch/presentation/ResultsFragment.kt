package com.example.imagesearch.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.imagesearch.R
import com.example.imagesearch.app.LOG_TAG
import com.example.imagesearch.presentation.viewmodel.ResultsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsFragment : Fragment(R.layout.fragment_results) {

    private val resultsViewModel: ResultsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            val toolbar = findViewById<Toolbar>(R.id.resultsFragmentToolbar)
            val searchViewMenuItem = toolbar.menu.findItem(R.id.action_results_fragment_search)
            val searchView = searchViewMenuItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { onNewQuery(it) }
                    return true
                }

                override fun onQueryTextChange(newText: String?) = true

            })

            resultsViewModel.resultsLiveEvent.observe(viewLifecycleOwner) {
                Log.d(LOG_TAG, "results fetched: ${it.size}")
            }
        }
    }

    private fun onNewQuery(query: String) {
        resultsViewModel.getImages(query)
    }

}