package com.gox.shop.datamodel

data class CommonSuccessResponse(
    val error: List<Any?>? = listOf(),
    val message: String? = "",
    val responseData: List<Any?>? = listOf(),
    val statusCode: String? = "",
    val title: String? = ""
)