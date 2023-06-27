package com.gox.shop.views.login

import androidx.lifecycle.MutableLiveData
import com.gox.shop.BuildConfig
import com.gox.shop.application.AppController
import com.gox.shop.base.BaseViewModel
import com.gox.shop.datamodel.LoginModel
import com.gox.shop.interfaces.ApiListener
import com.gox.shop.repository.ShopRepository
import com.gox.shop.utils.Constants
import com.gox.shop.utils.PreferenceKey
import com.gox.shop.utils.SessionManager
import com.gox.shop.utils.WebApiConstants
import javax.inject.Inject

class  LoginViewModel:BaseViewModel<LoginNavigator>(){
    @Inject
    lateinit var  sessionManager: SessionManager

    @Inject
    lateinit var  shopRepository: ShopRepository

    init {
        AppController.appComponent.inject(this)
    }

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    var loginResponseData=MutableLiveData<LoginModel>()
    var loadingLiveData=MutableLiveData<Boolean>()


    fun validateInputs(){
       if(navigator.validate()){
           postLogin()
       }
    }

    fun postLogin(){
        loadingLiveData.value=true
         val params=HashMap<String,String>()
        params[WebApiConstants.LoginConstants.EMAIL]=email.value.toString()
        params[WebApiConstants.LoginConstants.PASSWORD]=password.value.toString()
        params[WebApiConstants.LoginConstants.DEVICETOKEN]=sessionManager.get(PreferenceKey.ACCESS_TOKEN)
        params[WebApiConstants.LoginConstants.DEVICETYPE]=Constants.DEVICETYPE
        params[WebApiConstants.AUTHKEY.SALT_KEY]=BuildConfig.SALT_KEY
        getCompositeDisposable().addAll(shopRepository.callLoginApi(params,object:ApiListener{
            override fun success(successData: Any) {
                loginResponseData.value=successData as LoginModel
                loadingLiveData.value=false
            }

            override fun fail(failData: Throwable) {
                navigator.showError(getErrorMessage(failData))
                loadingLiveData.value=false
            }

        }))
    }

    fun forgetPasswordFun(){
        navigator.forgetPasswordFun()
    }
}