package com.mrc.productsearchapplication.data

data class Filter(
    val `data`: List<DataX>,
    val horizontal: Boolean,
    val label: String,
    val name: String,
    val parameter: String,
    val parentFacets: List<Any>,
    val searchable: Boolean,
    val sublist: List<Sublist>,
    val theme: String,
    val type: String
)