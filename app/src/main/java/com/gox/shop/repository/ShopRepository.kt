package com.gox.shop.repository

import com.gox.shop.application.AppController
import com.gox.shop.dependencies.ApiCallServices.ApiService
import com.gox.shop.interfaces.ApiListener
import com.gox.shop.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ShopRepository {

    @Inject
    lateinit var apiService: ApiService

    init {
        AppController.appComponent.inject(this)
    }


    fun callLoginApi(params: HashMap<String, String>, apiListener: ApiListener): Disposable {
        return apiService.postLogin(params)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                apiListener.success(it)

            }, {
                apiListener.fail(it)
            })
    }


    fun getIncomingOrders(params: HashMap<String, String>, apiListener: ApiListener): Disposable {
        return apiService.getIncomingOrders(Constants.WebConstants.ORDERED)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                apiListener.success(it)
            },{
                apiListener.fail(it )
            })
    }


    fun logout(apiListener: ApiListener):Disposable{
        return  apiService.postLogout()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                apiListener.success(it)
            },{
                apiListener.fail(it)
            })
    }


    fun accpetOrder(params:HashMap<String,String>,apiListener: ApiListener):Disposable{
        return  apiService.acceptOrder(params)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                apiListener.success(it)
            },{
                apiListener.fail(it)
            })
    }

    fun cancelOrder(params:HashMap<String,String>,apiListener: ApiListener):Disposable{
        return  apiService.cancelOrder(params)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                apiListener.success(it)
            },{
                apiListener.fail(it)
            })
    }

    fun changePassword(params:HashMap<String,String>,apiListener: ApiListener):Disposable{
        return  apiService.changePassword(params)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                apiListener.success(it)
            },{
                apiListener.fail(it)
            })
    }


}