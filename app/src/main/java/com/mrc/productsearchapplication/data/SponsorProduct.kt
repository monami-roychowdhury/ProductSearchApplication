package com.mrc.productsearchapplication.data

data class SponsorProduct(
    val destinationUrl: String,
    val imageUrl: String,
    val mrp: Double,
    val name: String,
    val rank: Int,
    val salePrice: Double,
    val sclid: String,
    val score: Int,
    val sellerId: String,
    val skuId: String,
    val uclid: String
)