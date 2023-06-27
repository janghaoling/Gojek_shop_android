package com.gox.shop.views.orderdetail

interface  OrderDetailNavigator {
   fun getErrorMessage(message:String)
    fun acceptOrder()
    fun cancelOrder()
}