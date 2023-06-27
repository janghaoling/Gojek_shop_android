package com.gox.shop.utils

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.annotation.MainThread
import com.gox.shop.application.AppController
import es.dmoral.toasty.Toasty
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class  CommanMethods {

    @Inject
    lateinit var dateTimeUtlity:DateTimeUtility

    @Inject
    lateinit var  application:Application

    @Inject
    lateinit var  sessionManager: SessionManager

    init {
        AppController.appComponent?.inject(this)
    }

    fun getNumberFormat(): NumberFormat? {
        val currency: String = sessionManager.get(PreferenceKey.CURRENCY,"$").toString()
        val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
        val decimalFormatSymbols = (numberFormat as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currencySymbol = currency
        numberFormat.decimalFormatSymbols = decimalFormatSymbols
        numberFormat.setMinimumFractionDigits(2)
        return numberFormat
    }

    @MainThread
    fun showToast(context: Context, message: String, isSuccess: Boolean) {
        if (isSuccess) Toasty.success(context, message, Toast.LENGTH_SHORT).show()
        else Toasty.error(context, message, Toast.LENGTH_SHORT).show()
    }

    @MainThread
    fun showNormalToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }



}