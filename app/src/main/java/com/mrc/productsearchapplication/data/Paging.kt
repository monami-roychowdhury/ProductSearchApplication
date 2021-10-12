package com.mrc.productsearchapplication.data

data class Paging(
    val item_per_page: Int,
    val page: Int,
    val total_item: Int,
    val total_page: Int
)