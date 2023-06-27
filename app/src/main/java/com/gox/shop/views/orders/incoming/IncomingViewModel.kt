package com.gox.shop.views.orders.incoming

import android.provider.SyncStateContract
import androidx.lifecycle.MutableLiveData
import com.gox.shop.application.AppController
import com.gox.shop.base.BaseViewModel
import com.gox.shop.datamodel.NewOrderModel
import com.gox.shop.interfaces.ApiListener
import com.gox.shop.repository.ShopRepository
import com.gox.shop.utils.Constants
import javax.inject.Inject

class IncomingViewModel : BaseViewModel<IncomingNavigator>() {
    @Inject
    lateinit var shopRepository: ShopRepository

    init {
        AppController.appComponent.inject(this)
    }

    var newOrderLiveData = MutableLiveData<NewOrderModel>()

    fun getIncomingOrders() {
        val params= HashMap<String,String>(
        )
        params["type"]=Constants.WebConstants.ORDERED
        getCompositeDisposable().addAll(shopRepository.getIncomingOrders(params,
            object : ApiListener {
                override fun success(successData: Any) {
                    newOrderLiveData.value = successData as NewOrderModel
                }

                override fun fail(failData: Throwable) {
                    navigator.showError(getErrorMessage(failData))
                }

            }))
    }


}