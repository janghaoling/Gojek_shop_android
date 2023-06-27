package com.gox.shop.views.orderdetail

import androidx.lifecycle.MutableLiveData
import com.gox.shop.application.AppController
import com.gox.shop.base.BaseViewModel
import com.gox.shop.datamodel.CommonSuccessResponse
import com.gox.shop.datamodel.NewOrderModel
import com.gox.shop.interfaces.ApiListener
import com.gox.shop.repository.ShopRepository
import com.gox.shop.utils.Constants
import com.gox.shop.utils.PreferenceKey
import com.gox.shop.utils.SessionManager
import com.gox.shop.utils.WebApiConstants
import javax.inject.Inject

class OrderDetailViewModel : BaseViewModel<OrderDetailNavigator>() {

    @Inject
    lateinit var shopRepository: ShopRepository

    @Inject
    lateinit var  sessionManager: SessionManager

    init {
        AppController.appComponent.inject(this)
    }

    var incomingOrderModelLd = MutableLiveData<NewOrderModel.ResponseData>()
    var commonRespnoseLd = MutableLiveData<CommonSuccessResponse>()
    var preparationTime = MutableLiveData<String>()
    var storeID = MutableLiveData<String>()
    var userID = MutableLiveData<String>()
    var loadingProgressLd=MutableLiveData<Boolean>()


    fun acceptOrders() {
        navigator.acceptOrder()
    }

    fun cancelOrders() {
        navigator.cancelOrder()
    }


    fun acceptOrder() {
        loadingProgressLd.value=true
        val params = HashMap<String, String>()
        params[WebApiConstants.OrderAccept.STOREID] = storeID.value.toString()
        params[WebApiConstants.OrderAccept.USERID] = userID.value.toString()
        params[WebApiConstants.OrderAccept.COOKTIME] = preparationTime.value.toString()
        getCompositeDisposable().addAll(shopRepository.accpetOrder(params, object : ApiListener {
            override fun success(successData: Any) {
                loadingProgressLd.value=false
                commonRespnoseLd.value = successData as CommonSuccessResponse

            }

            override fun fail(failData: Throwable) {
                loadingProgressLd.value=false
                navigator.getErrorMessage(getErrorMessage(failData))
            }

        }))

    }

    fun cancelOrder() {
        loadingProgressLd.value=true
        val params = HashMap<String, String>()
        params[WebApiConstants.OrderAccept.ID] = storeID.value.toString()
        params[WebApiConstants.OrderAccept.USERID] = userID.value.toString()
        val storeID=sessionManager.get<Int>(PreferenceKey.SHOP_ID)
        params[WebApiConstants.OrderAccept.SHOPID] = storeID.toString()
        getCompositeDisposable().addAll(shopRepository.cancelOrder(params, object : ApiListener {
            override fun success(successData: Any) {
                loadingProgressLd.value=false
                commonRespnoseLd.value = successData as CommonSuccessResponse
            }

            override fun fail(failData: Throwable) {
                loadingProgressLd.value=false
                navigator.getErrorMessage(getErrorMessage(failData))
            }

        }))
    }
}