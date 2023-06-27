package com.gox.shop.utils

import android.util.Base64
import com.gox.shop.BuildConfig
import com.gox.shop.application.AppController
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class Constants {

    @Inject
    lateinit var  sessionManager: SessionManager
    init {
        AppController.appComponent.inject(this)
    }

    companion object {

        const val APP_NAME = "XJEK_SHOP"
        const val  DEVICETYPE="ANDROID"
        val baseUrl: String = "https://api.gox.network/"
        val utcTZ: TimeZone = TimeZone.getTimeZone("UTC")
        val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val monthViewFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())
        val dayViewFormat = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
        val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val dateStringFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val monthStringFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateTimeDiaryFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.getDefault())
        val UDC_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val myPostDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        var COMPANY_ID: String =
            String(Base64.decode(BuildConfig.SALT_KEY, Base64.DEFAULT), Charset.defaultCharset())

        const val CONNECTION_TIMEOUT = 30L
        const val READ_TIMEOUT = 30L
        const val WRITE_TIMEOUT = 30L
    }

    // Socket
    object RoomName {
        var NEW_REQ: String = "newRequest"
        var JOINSHOPROOM:String="joinShopRoom"
    }

    //Socket RoomConstants
    object RoomConstants {
        var shopID =PreferencesHelper.get(PreferenceKey.SHOP_ID, 0)
    }


    object RoomID {
        var TRANSPORT_ROOM: String = "room_${COMPANY_ID}_shop_${RoomConstants.shopID}"
    }


    object  WebConstants{
        var ORDERED="ORDERED"
        var ACCEPTED="ACCEPTED"
    }

    //Menus
    object MenunNames {
        var DashboardFrag = "DashBoardFrag"
        var HisotryFrag = "HisotryFrag"
        var AccountFrag = "AccountFrag"
        var OrderFrag="OrderFrag"
    }



    //Error
    object ThrowableErrorMsg{
        var UNSUPPORTEDTYPE="Value type is undefined"
    }
}