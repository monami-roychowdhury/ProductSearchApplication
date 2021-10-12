package com.mrc.productsearchapplication.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mrc.productsearchapplication.R
import com.mrc.productsearchapplication.data.Product
import com.mrc.productsearchapplication.databinding.ListFragmentLayoutBinding
import com.mrc.productsearchapplication.viewmodel.ProductSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment(R.layout.list_fragment_layout),
    ProductsListAdapter.OnItemClickListener {

    private val viewModel by viewModels<ProductSearchViewModel>()
    private var _binding: ListFragmentLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProductsListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ListFragmentLayoutBinding.bind(view)
        adapter = ProductsListAdapter(this)
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )
            recyclerView.itemAnimator = null
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ProductsLoadStateAdapter { adapter.retry() },
                footer = ProductsLoadStateAdapter { adapter.retry() }
            )
            buttonRetry.setOnClickListener {
                adapter.retry()
            }
        }
        viewModel.products.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                //If no results found
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchProducts(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.length >= 3) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchProducts(newText)
                }
                return true
            }

        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_toggle_view) {
            val isSwitched = adapter.toggleItemViewType()
            if (isSwitched) {
                item.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_view_grid)
                binding.recyclerView.layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL, false
                )
            } else {
                item.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_view_list)
                val columnCount = resources.getInteger(R.integer.grid_column_count)
                binding.recyclerView.layoutManager =
                    GridLayoutManager(requireContext(), columnCount)
            }
            adapter.notifyDataSetChanged()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(product: Product) {
        Snackbar.make(
            binding.root, "Item Clicked: " + product.name,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}