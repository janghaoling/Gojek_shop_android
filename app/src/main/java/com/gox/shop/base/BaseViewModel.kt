package com.gox.shop.base

import androidx.lifecycle.ViewModel
import com.gox.shop.BuildConfig
import com.gox.shop.utils.NetworkError
import com.google.gson.JsonSyntaxException
import io.reactivex.disposables.CompositeDisposable
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.lang.ref.WeakReference
import java.net.SocketTimeoutException

open class BaseViewModel<N>:ViewModel(){

    private var compositeDisposable = CompositeDisposable()
    private lateinit var mNavigator: WeakReference<N>
    var navigator: N
        get() = mNavigator.get()!!
        set(navigator) {
            this.mNavigator = WeakReference(navigator)
        }

    fun getCompositeDisposable() = compositeDisposable

      override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun getErrorMessage(e: Throwable): String {
        return when (e) {
            is JsonSyntaxException -> {
                if (BuildConfig.DEBUG) e.message.toString()
                else NetworkError.DATA_EXCEPTION
            }
            is HttpException -> { if(e.code()==401) getErrorMessage(e.response()!!.errorBody()!!) else ""}
            is SocketTimeoutException -> NetworkError.TIME_OUT
            is IOException -> NetworkError.IO_EXCEPTION
            else -> NetworkError.SERVER_EXCEPTION
        }
    }

    private fun getErrorMessage(responseBody: ResponseBody): String {
        return try {
            val jsonObject = JSONObject(responseBody.string())
            jsonObject.getString("message")
        } catch (e: Exception) {
            e.message!!
        }
    }
}