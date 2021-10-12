package com.mrc.productsearchapplication.data

data class QuickFilter(
    val `data`: List<DataXXX>,
    val horizontal: Boolean,
    val label: String,
    val name: String,
    val parameter: String,
    val searchable: Boolean,
    val theme: String,
    val type: String
)