package com.gox.shop.views.forgotPasswordActivity

import android.app.Activity
import android.util.TypedValue
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gox.shop.R
import com.gox.shop.base.BaseActivity
import com.gox.shop.databinding.ActivityForgotpasswordBinding
import kotlinx.android.synthetic.main.activity_forgotpassword.view.*
import kotlinx.android.synthetic.main.layout_header.view.*

class ForgotPasswordActivity : BaseActivity<ActivityForgotpasswordBinding>(),
    ForgotPasswordNavigator {


    lateinit var mViewDataBinding: ActivityForgotpasswordBinding
    lateinit var forgotPasswordViewModel: ForgotPasswordViewModel
    override fun getLayoutId(): Int = R.layout.activity_forgotpassword



    override fun initView(mViewDataBinding: ViewDataBinding?) {

        this.mViewDataBinding = mViewDataBinding as ActivityForgotpasswordBinding
        forgotPasswordViewModel = ViewModelProviders.of(this)[ForgotPasswordViewModel::class.java]
        forgotPasswordViewModel.navigator = this
        this.mViewDataBinding.forgotPasswordViewModel = forgotPasswordViewModel
        this.mViewDataBinding.forgotpasswordToolbar.forgotpassword_toolbar.tbr_title.text=(getString(R.string.forgot_password).removeSuffix("?"))
        this.mViewDataBinding.forgotpasswordToolbar.forgotpassword_toolbar.setOnClickListener { view ->
            finish()
        }

        forgotPasswordViewModel.loadingProgress.observe(this, Observer<Boolean> {
            loadingObservable.value = it
        })



        forgotPasswordViewModel.errorResponse.observe(this@ForgotPasswordActivity, Observer<String> { message ->

        })

    }

    private fun dpsToPixels(activity: Activity, dps: Int): Int {
        val r = activity.resources
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps.toFloat(), r.displayMetrics).toInt()
    }


}