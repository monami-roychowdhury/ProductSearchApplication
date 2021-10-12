package com.mrc.productsearchapplication.data

data class DataX(
    val code: String,
    val disabled: Boolean,
    val id: String,
    val indexName: String,
    val label: String,
    val level: Int,
    val promoBatchUrl: String,
    val query: String,
    val restrictedCategory: Boolean,
    val selected: Boolean,
    val subCategory: List<Any>,
    val subCategorySelected: Boolean,
    val tooltip: String,
    val tooltipText: String,
    val tooltipUrl: String,
    val value: String
)