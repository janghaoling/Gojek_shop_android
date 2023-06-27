package com.gox.shop.utils

class WebApiConstants {

    object AUTHKEY {
        val SALT_KEY = "salt_key"
    }

    object LoginConstants {
        val EMAIL = "email"
        val PASSWORD = "password"
        val DEVICETYPE = "device_type"
        val DEVICETOKEN = "device_token"
    }

    object OrderAccept {
        val STOREID="store_order_id"
        val COOKTIME="cooking_time"
        val USERID="user_id"
        val SHOPID="store_id"
        val   ID="id"
    }

    object ChangePassword{
        val OLD_PASSWORD="old_password"
        val NEW_PASSWORD="password"
        val CONFIRM_PASSWORD="password_confirmation"
    }

}