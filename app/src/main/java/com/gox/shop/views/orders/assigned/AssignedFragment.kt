package com.gox.shop.views.orders.assigned

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.gox.shop.R
import com.gox.shop.base.BaseFragment
import com.gox.shop.databinding.FragmentOrderedListBinding
import com.gox.shop.databinding.FragmentOrderedListBindingImpl

class AssignedFragment : BaseFragment<FragmentOrderedListBindingImpl>() {
    private lateinit var fragmentOrderedListBinding: FragmentOrderedListBinding
    private lateinit var assignedViewModel: AssignedViewModel

    override fun initView(mRootView: View?, mViewDataBinding: ViewDataBinding?) {
        fragmentOrderedListBinding = mViewDataBinding as FragmentOrderedListBinding
        assignedViewModel = ViewModelProviders.of(this).get(AssignedViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_ordered_list
    }

}