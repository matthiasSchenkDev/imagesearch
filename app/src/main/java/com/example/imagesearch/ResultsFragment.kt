package com.example.imagesearch

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

class ResultsFragment : Fragment(R.layout.fragment_results) {

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
        }
    }

    private fun onNewQuery(query: String) {
        TODO("Not yet implemented")
    }

}