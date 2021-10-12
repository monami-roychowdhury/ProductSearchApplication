package com.mrc.productsearchapplication.api

import com.mrc.productsearchapplication.data.Data

data class ApiResponse(
    val code: Int,
    val data: Data,
    val status: String
)