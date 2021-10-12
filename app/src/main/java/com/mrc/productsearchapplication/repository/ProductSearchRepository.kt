package com.mrc.productsearchapplication.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.mrc.productsearchapplication.api.ProductSearchApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductSearchRepository @Inject constructor(private val productSearchApi: ProductSearchApi) {

    fun getProducts(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 24,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductSearchPagingSource(productSearchApi, query) }
        ).liveData


}