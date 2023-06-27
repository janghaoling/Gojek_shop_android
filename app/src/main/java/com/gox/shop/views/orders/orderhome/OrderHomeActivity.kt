package com.gox.shop.views.orders.orderhome

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.gox.shop.R
import com.gox.shop.base.BaseActivity
import com.gox.shop.databinding.FragmentHomeBinding


class  OrderHomeActivity:BaseActivity<FragmentHomeBinding>(){
    private  lateinit var  activityOrderPageBinding:FragmentHomeBinding
    private  lateinit var  orderHomeViewModel:OrderHomeViewModel




    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }
    override fun initView(mViewDataBinding: ViewDataBinding?) {
        activityOrderPageBinding=mViewDataBinding as FragmentHomeBinding
        orderHomeViewModel=ViewModelProviders.of(this).get(OrderHomeViewModel::class.java)
        initViewPager()
    }

    fun initViewPager(){
        
    }



}