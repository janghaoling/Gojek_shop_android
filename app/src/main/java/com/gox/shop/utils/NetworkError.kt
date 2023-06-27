package com.gox.shop.utils

object NetworkError {
    const val TIME_OUT = "Network Timeout. Retry again!"
    const val IO_EXCEPTION = "Internal Server Error. Retry later"
    const val SERVER_EXCEPTION = "Something Went Wrong On Server. Please try later"
    const val DATA_EXCEPTION = "Server data mismatched"
}