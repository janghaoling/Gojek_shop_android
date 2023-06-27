package com.gox.shop.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    private var mActivity: BaseActivity<ViewDataBinding>? = null
    private var mViewDataBinding: T? = null
    private var baseLiveDataLoading: MutableLiveData<Boolean>? = MutableLiveData()
    private   var  rootView:View?=null
    protected abstract fun initView(
        mRootView: View?,
        mViewDataBinding: ViewDataBinding?
    )


    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            mActivity = context as BaseActivity<ViewDataBinding>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(rootView==null) {
            mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
            rootView = mViewDataBinding!!.getRoot()
        }else{

        }

        return  rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(mViewDataBinding!!.root, mViewDataBinding)
        baseLiveDataLoading!!.observe(this, Observer<Boolean> {

            if (it == true) {

            } else {

            }

        })

    }

    override fun onDetach() {
        super.onDetach()
    }
}