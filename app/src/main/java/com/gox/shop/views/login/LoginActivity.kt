package com.gox.shop.views.login

import android.content.Context
import android.content.Intent
import android.util.Patterns
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gox.shop.R
import com.gox.shop.application.AppController
import com.gox.shop.base.BaseActivity
import com.gox.shop.databinding.ActivityLoginBinding
import com.gox.shop.datamodel.LoginModel
import com.gox.shop.utils.CommanMethods
import com.gox.shop.utils.PreferenceKey
import com.gox.shop.utils.SessionManager
import com.gox.shop.views.dashboard.DashboardActivity
import com.gox.shop.views.forgotPasswordActivity.ForgotPasswordActivity
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding>(), LoginNavigator {
    @Inject
    lateinit var commanMethods: CommanMethods

    @Inject
    lateinit var sessionManager: SessionManager

    @Inject
    lateinit var context: Context

    private lateinit var activityLoginBinding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel


    init {
        AppController.appComponent.inject(this)
    }

    override fun getLayoutId() = R.layout.activity_login

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        activityLoginBinding = mViewDataBinding as ActivityLoginBinding
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginViewModel.navigator = this
        activityLoginBinding.setLifecycleOwner(this)
        activityLoginBinding.loginViewModel = loginViewModel
        getObserverValues()
    }

    override fun showError(errorMsg: String) {
        commanMethods.showToast(context, errorMsg, false)
    }

    override fun validate(): Boolean {
        if (loginViewModel.email.value.isNullOrBlank()) {
            val message = resources.getString(R.string.email_empty)
            commanMethods.showToast(context, message, false)
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(loginViewModel.email.value!!.trim())
                .matches()
        ) {
            val message = resources.getString(R.string.email_invalid)
            commanMethods.showToast(context, message, false)
            return false
        } else if (loginViewModel.password.value.isNullOrBlank()) {
            val message = resources.getString(R.string.password_empty)
            commanMethods.showToast(context, message, false)
            return false
        }

        return true
    }

    override fun forgetPasswordFun() {
       startActivity(Intent(this,ForgotPasswordActivity::class.java))
    }

    fun getObserverValues() {


        loginViewModel.loginResponseData.observe(this, Observer<LoginModel> {
            if (it != null && it.statusCode.equals("200")) {
                sessionManager.put(PreferenceKey.ACCESS_TOKEN, it.responseData!!.access_token)
                sessionManager.put(PreferenceKey.SHOP_ID, it.responseData!!.user!!.id)
                sessionManager.put(PreferenceKey.CURRENCY, "" + it.responseData!!.user!!.currency)

                val dashBoardIntent = Intent(this, DashboardActivity::class.java)
                startActivity(dashBoardIntent)
                finish()
            }
        })

        loginViewModel.loadingLiveData.observe(this, Observer<Boolean> {
            if (it) {
                baseLiveDataLoading.value = true
            } else {
                baseLiveDataLoading.value = false
            }
        })

    }

}