package com.example.imagesearch.presentation

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.R
import com.example.imagesearch.app.hide
import com.example.imagesearch.app.show
import com.example.imagesearch.presentation.viewmodel.ResultsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsFragment : Fragment(R.layout.fragment_results) {

    companion object {
        private const val INITIAL_QUERY = "fruits"
    }

    private val resultsViewModel: ResultsViewModel by viewModels()

    private lateinit var toolbar: Toolbar
    private lateinit var list: RecyclerView
    private lateinit var loadingSpinner: ProgressBar

    private lateinit var imageListAdapter: ImageListAdapter
    private lateinit var searchView: SearchView

    private var isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            toolbar = findViewById(R.id.resultsFragmentToolbar)
            loadingSpinner = findViewById(R.id.loadingSpinner)
            list = findViewById(R.id.imagesList)

            setupToolbar()
            setupList()

            resultsViewModel.resultsLiveEvent.observe(viewLifecycleOwner) {
                isLoading = false
                imageListAdapter.removeLoadingItem()
                imageListAdapter.submitList(it)
                loadingSpinner.hide()
                list.show()
            }

            searchView.setQuery(INITIAL_QUERY, true)
        }
    }

    private fun setupList() {
        imageListAdapter = ImageListAdapter()
        imageListAdapter.onItemClickListener = { showDetailsNavigationDialog(it) }
        list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = imageListAdapter
            addOnScrollListener(PaginationScrollListener {
                if (!isLoading) {
                    isLoading = true
                    imageListAdapter.addLoadingItem()
                    resultsViewModel.getMoreImages()
                }
            })
        }
    }

    private fun showDetailsNavigationDialog(imageEntity: ImageEntity) {
        val builder = AlertDialog.Builder(requireContext()).apply {
            setMessage(R.string.dialog_message_more_details)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                val action = ResultsFragmentDirections.actionResultsFragmentToDetailsFragment()
                findNavController().navigate(action)
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
        }
        val alert = builder.create()
        alert.show()
    }

    private fun setupToolbar() {
        val searchViewMenuItem = toolbar.menu.findItem(R.id.action_results_fragment_search)
        searchView = searchViewMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    isLoading = true
                    list.hide()
                    loadingSpinner.show()
                    resultsViewModel.getImages(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?) = true
        })
    }

}