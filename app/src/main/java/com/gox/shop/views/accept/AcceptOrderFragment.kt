package com.gox.shop.views.accept

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.gox.base.base.BaseDialogFragment
import com.gox.shop.R
import com.gox.shop.application.AppController
import com.gox.shop.base.BaseFragment
import com.gox.shop.databinding.ActivityRequestAcceptBinding
import com.gox.shop.databinding.FragmentPrepareTimeBinding
import com.gox.shop.utils.CommanMethods
import com.gox.shop.views.orderdetail.OrderDetailViewModel
import javax.inject.Inject

class  AcceptOrderFragment:BaseDialogFragment<FragmentPrepareTimeBinding>(),AcceptOrderNavigator{

    private  lateinit var  fragmentPrepareTimeBinding: FragmentPrepareTimeBinding
    private  lateinit var  acceptOrderViewModel: AcceptOrderViewModel
    private  lateinit var   orderDetailViewModel: OrderDetailViewModel


    @Inject
    lateinit var  commanMethods: CommanMethods

    init {
        AppController.appComponent.inject(this)
    }
    override fun getLayout(): Int {
        return R.layout.fragment_prepare_time
    }

    override fun initView(viewDataBinding: ViewDataBinding, view: View) {
        fragmentPrepareTimeBinding =viewDataBinding as FragmentPrepareTimeBinding
        acceptOrderViewModel=ViewModelProviders.of(this).get(AcceptOrderViewModel::class.java)
        fragmentPrepareTimeBinding.acceptOrderViewModel=acceptOrderViewModel
        orderDetailViewModel=ViewModelProviders.of(activity!!).get(OrderDetailViewModel::class.java)
        fragmentPrepareTimeBinding.setLifecycleOwner(this)
        acceptOrderViewModel.navigator=this


    }

    override fun closeDialogWithPreparetime() {
        if(acceptOrderViewModel.prePareTime.value!!.toInt()>0)
        orderDetailViewModel.preparationTime.value=acceptOrderViewModel.prePareTime.value
        else
            commanMethods.showToast(activity as Context,"please choose time",false)
        dialog!!.dismiss()

    }

    override fun closeDialogWithoutTime() {
        dialog!!.dismiss()
   }

}