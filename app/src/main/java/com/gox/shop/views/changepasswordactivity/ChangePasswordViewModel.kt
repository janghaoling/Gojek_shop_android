package com.gox.app.ui.changepasswordactivity

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.gox.shop.BuildConfig
import com.gox.shop.application.AppController
import com.gox.shop.base.BaseViewModel
import com.gox.shop.datamodel.CommonSuccessResponse
import com.gox.shop.dependencies.ApiCallServices.ApiService
import com.gox.shop.interfaces.ApiListener
import com.gox.shop.repository.ShopRepository
import com.gox.shop.utils.WebApiConstants
import javax.inject.Inject


class ChangePasswordViewModel : BaseViewModel<ChangePasswordNavigator>() {

    @Inject
    lateinit var  apiService: ApiService

    @Inject
    lateinit var shopRepository: ShopRepository

    init {
        AppController.appComponent.inject(this)
    }

    var oldPassword: ObservableField<String> = ObservableField("")
    var newPassword: ObservableField<String> = ObservableField("")
    var confrimPassword: ObservableField<String> = ObservableField("")

    var loadingProgress = MutableLiveData<Boolean>()
    var changePasswordResponse = MutableLiveData<CommonSuccessResponse>()
    var errorResponse = MutableLiveData<String>()


    fun changePassword() {
        if (navigator.checkValidation()) {
            loadingProgress.value = true
            val params = HashMap<String, String>()
            params[BuildConfig.SALT_KEY] = "MQ=="
            params[WebApiConstants.ChangePassword.OLD_PASSWORD] = oldPassword.get().toString()
            params[WebApiConstants.ChangePassword.NEW_PASSWORD] = newPassword.get().toString()
            params[WebApiConstants.ChangePassword.CONFIRM_PASSWORD] = confrimPassword.get().toString()
            getCompositeDisposable().addAll(shopRepository.changePassword(params,object:ApiListener{
                override fun success(successData: Any) {
                    changePasswordResponse.postValue(successData as CommonSuccessResponse)
                }

                override fun fail(failData: Throwable) {
                   navigator.showError(getErrorMessage(failData))
                }

            }))
        }


    }
}