package com.example.imagesearch.presentation.fragment

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.R
import com.example.imagesearch.app.hide
import com.example.imagesearch.app.hideSoftKeyboard
import com.example.imagesearch.app.show
import com.example.imagesearch.presentation.common.ImageListAdapter
import com.example.imagesearch.presentation.common.PaginationScrollListener
import com.example.imagesearch.presentation.viewmodel.ResultListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultListFragment : Fragment(R.layout.fragment_result_list) {

    companion object {
        private const val INITIAL_QUERY = "fruits"
    }

    private val resultListViewModel: ResultListViewModel by viewModels()

    private lateinit var toolbar: Toolbar
    private lateinit var list: RecyclerView
    private lateinit var loadingSpinner: ProgressBar
    private lateinit var errorView: View

    private lateinit var imageListAdapter: ImageListAdapter
    private lateinit var searchView: SearchView

    private var isLoading = false
    private var query: String = INITIAL_QUERY

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            toolbar = findViewById(R.id.resultListFragmentToolbar)
            loadingSpinner = findViewById(R.id.loadingSpinner)
            list = findViewById(R.id.imagesList)
            errorView = findViewById(R.id.errorView)

            setupToolbar()
            setupList()

            resultListViewModel.resultListLiveEvent.observe(viewLifecycleOwner) { images ->
                isLoading = false
                imageListAdapter.removeLoadingItem()
                loadingSpinner.hide()
                images?.let {
                    imageListAdapter.submitList(it)
                    errorView.hide()
                    list.show()
                } ?: showCurrentListOrError()
            }

            resultListViewModel.resultListLiveEvent.value?.let {
                imageListAdapter.submitList(it)
            } ?: searchView.setQuery(INITIAL_QUERY, true)
        }
    }

    private fun showCurrentListOrError() {
        if (imageListAdapter.currentList.isEmpty() || query != resultListViewModel.currentResultsQuery) {
            errorView.show()
        } else list.show()
    }

    private fun setupList() {
        imageListAdapter = ImageListAdapter()
        imageListAdapter.onItemClickListener = { showDetailsNavigationDialog(it) }
        list.apply {
            val layoutManager = LinearLayoutManager(requireContext())
            this.layoutManager = layoutManager
            addItemDecoration(DividerItemDecoration(this.context, layoutManager.orientation))
            adapter = imageListAdapter
            addOnScrollListener(PaginationScrollListener {
                if (!isLoading) {
                    isLoading = true
                    imageListAdapter.addLoadingItem()
                    resultListViewModel.getMoreImages()
                }
            })
        }
    }

    private fun showDetailsNavigationDialog(imageId: Int) {
        val builder = AlertDialog.Builder(requireContext()).apply {
            setMessage(R.string.dialog_message_more_details)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                val action =
                    ResultListFragmentDirections.actionResultsFragmentToDetailsFragment(imageId)
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
        val searchViewMenuItem = toolbar.menu.findItem(R.id.action_result_list_fragment_search)
        searchView = searchViewMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                requireActivity().hideSoftKeyboard()
                if (!query.isNullOrEmpty()) {
                    isLoading = true
                    errorView.hide()
                    list.hide()
                    loadingSpinner.show()
                    this@ResultListFragment.query = query
                    resultListViewModel.getImages(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?) = true
        })
    }

}