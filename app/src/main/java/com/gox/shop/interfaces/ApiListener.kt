package com.gox.shop.interfaces

interface ApiListener {
    fun success(successData: Any)
    fun fail(failData: Throwable)
}