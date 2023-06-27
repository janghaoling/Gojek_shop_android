package com.gox.shop.views.forgotPasswordActivity

import androidx.lifecycle.MutableLiveData
import com.gox.shop.base.BaseViewModel

class ForgotPasswordViewModel : BaseViewModel<ForgotPasswordNavigator>() {
    var email = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    lateinit var country_code: String
    var accountype: String = "email"

    //Thease two live data common for all api calls
    var errorResponse = MutableLiveData<String>()
    var loadingProgress = MutableLiveData<Boolean>()

}