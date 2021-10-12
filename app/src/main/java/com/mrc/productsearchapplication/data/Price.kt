package com.mrc.productsearchapplication.data

data class Price(
    val discount: Int,
    val minPrice: Double,
    val offerPriceDisplay: String?,
    val priceDisplay: String,
    val strikeThroughPriceDisplay: String?
)