package com.mrc.productsearchapplication.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mrc.productsearchapplication.api.ProductSearchApi
import com.mrc.productsearchapplication.data.Product
import retrofit2.HttpException
import java.io.IOException


private const val STARTING_PAGE_INDEX = 0

class ProductSearchPagingSource(
    private val productSearchApi: ProductSearchApi,
    private val query: String
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = productSearchApi.getProducts(query, position, params.loadSize)
            val products = response.data.products
            LoadResult.Page(
                data = products,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (products.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        TODO("Not yet implemented")
    }


}