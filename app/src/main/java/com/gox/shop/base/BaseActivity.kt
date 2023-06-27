package com.gox.shop.base

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.gox.shop.application.AppController
import com.gox.shop.dependencies.component.AppComponent
import com.gox.shop.utils.CommanMethods
import com.gox.shop.utils.CustomLoaderDialog
import javax.inject.Inject

abstract  class BaseActivity<T:ViewDataBinding>:AppCompatActivity(){


    protected var baseLiveDataLoading = MutableLiveData<Boolean>()
    private var mViewDataBinding: T? = null
    protected abstract fun initView(mViewDataBinding: ViewDataBinding?)
    private lateinit var mCustomLoaderDialog: CustomLoaderDialog



    @LayoutRes
    protected abstract fun getLayoutId(): Int

    val loadingObservable: MutableLiveData<*> get() = baseLiveDataLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        initView(mViewDataBinding)
        mCustomLoaderDialog = CustomLoaderDialog(this, true)

        baseLiveDataLoading.observe(this, Observer<Boolean> {
            if(it) showLoading()
            else   hideLoading()
        })
    }

    private fun showLoading() {
        try {
            if (mCustomLoaderDialog.window != null) {
                mCustomLoaderDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                mCustomLoaderDialog.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun hideLoading() = mCustomLoaderDialog.cancel()

}