package com.gox.shop.views.splash

import android.content.Intent
import android.os.Handler
import androidx.databinding.ViewDataBinding
import com.gox.shop.R
import com.gox.shop.application.AppController
import com.gox.shop.base.BaseActivity
import com.gox.shop.databinding.ActivitySplashBinding
import com.gox.shop.utils.PreferenceKey
import com.gox.shop.utils.SessionManager
import com.gox.shop.views.dashboard.DashboardActivity
import com.gox.shop.views.login.LoginActivity
import javax.inject.Inject

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    @Inject
    lateinit var  sessionManager: SessionManager

    init {
        AppController.appComponent.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        val handler = Handler()
        handler.postDelayed({ callDashBoardActivity() }, 3000)
    }

    fun callDashBoardActivity() {
        val token=sessionManager.get<String>(PreferenceKey.ACCESS_TOKEN)
        if(!token.isNullOrEmpty()) {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}