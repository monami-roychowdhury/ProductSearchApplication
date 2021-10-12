package com.mrc.productsearchapplication.data

data class Product(
    val allCategories: List<String>,
    val attributes: List<Attribute>,
    val badge: Badge,
    val brand: String,
    val campaignInfo: CampaignInfo,
    val debugData: DebugData,
    val defaultSku: String,
    val expandedProducts: List<Any>,
    val formattedId: String,
    val id: String,
    val images: List<String>,
    val isCheap: Boolean,
    val itemCount: Int,
    val itemSku: String,
    val level0Id: String,
    val location: String,
    val merchantCode: String,
    val merchantVoucherCount: Int,
    val name: String,
    val numLocations: Int,
    val official: Boolean,
    val preorder: Boolean,
    val price: Price,
    val productFeatures: String,
    val promoBadgeUrl: String,
    val promoEndTime: Long,
    val review: Review,
    val rootCategory: RootCategory,
    val sku: String,
    val soldRangeCount: SoldRangeCount,
    val status: String,
    val storeClosingInfo: StoreClosingInfo,
    val tags: List<String>,
    val uniqueSellingPoint: String,
    val url: String
)