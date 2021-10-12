package com.mrc.productsearchapplication.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mrc.productsearchapplication.repository.ProductSearchRepository
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductSearchViewModel @Inject constructor(
    private val repository: ProductSearchRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val products = currentQuery.switchMap { queryString ->
        repository.getProducts(queryString).cachedIn(viewModelScope)
    }

    fun searchProducts(query: String) {
        currentQuery.value = query
    }


    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "samsung"
    }


}