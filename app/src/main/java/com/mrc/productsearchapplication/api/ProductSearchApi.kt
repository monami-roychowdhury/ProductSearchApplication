package com.mrc.productsearchapplication.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ProductSearchApi {

    companion object {
        const val BASE_URL = "https://www.blibli.com/backend/"
    }


    @Headers("User-Agent: BlibliAndroid/7.9.0(3465) 4d4412b5-898a-4e75-a446-c22b977ee531 Dalvik/2.1.0 (Linux; U; Android 10; Mi A2 Build/QKQ1.190910.002)")
    @GET("search/products")
    suspend fun getProducts(
        @Query("searchTerm") searchTerm: String,
        @Query("start") start: Int,
        @Query("itemPerPage") itemPerPage: Int
    ): ApiResponse

}