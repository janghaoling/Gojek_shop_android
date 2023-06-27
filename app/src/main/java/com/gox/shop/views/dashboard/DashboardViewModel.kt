package com.gox.shop.views.dashboard

import androidx.lifecycle.MutableLiveData
import com.gox.shop.application.AppController
import com.gox.shop.base.BaseViewModel
import com.gox.shop.datamodel.CommonSuccessResponse
import com.gox.shop.interfaces.ApiListener
import com.gox.shop.repository.ShopRepository
import javax.inject.Inject

class  DashboardViewModel: BaseViewModel<DashboardNavigator>(){

    @Inject
    lateinit var  shopRepository: ShopRepository

    init {
        AppController.appComponent.inject(this)
    }
    var logoutResPonseLiveData=MutableLiveData<CommonSuccessResponse>()
    var loadingLiveData=MutableLiveData<Boolean>()

    fun logout(){
        loadingLiveData.value=true
        getCompositeDisposable().addAll(shopRepository.logout(object:ApiListener{
            override fun success(successData: Any) {
                 loadingLiveData.value=false
                logoutResPonseLiveData.value=successData as CommonSuccessResponse
            }

            override fun fail(failData: Throwable) {
                loadingLiveData.value=false
            }

        }))
    }

}