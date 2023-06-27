package com.gox.shop.views.orderdetail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gox.shop.R
import com.gox.shop.application.AppController
import com.gox.shop.base.BaseActivity
import com.gox.shop.databinding.ActivityRequestAcceptBinding
import com.gox.shop.datamodel.CommonSuccessResponse
import com.gox.shop.datamodel.NewOrderModel
import com.gox.shop.dependencies.component.AppComponent
import com.gox.shop.dependencies.modules.AppContainerModule_ProvidesDateTimeUtilityFactory
import com.gox.shop.utils.CommanMethods
import com.gox.shop.utils.ImageUtils
import com.gox.shop.views.accept.AcceptOrderFragment
import com.gox.shop.views.adapters.OrderListAdapter
import kotlinx.android.synthetic.main.header_toolbar.view.*
import kotlinx.android.synthetic.main.invoice.view.*
import javax.inject.Inject

class  OrderDetailActivity:BaseActivity<ActivityRequestAcceptBinding>(),OrderDetailNavigator{

    @Inject
    lateinit var  imageUtils: ImageUtils

    @Inject
    lateinit var  commanMethods: CommanMethods

    init {
        AppController.appComponent.inject(this)
    }

    private lateinit var  activityRequestAcceptBinding: ActivityRequestAcceptBinding
    private lateinit var   orderDetailViewModel: OrderDetailViewModel
    private  lateinit var  context: Context
    private   var  firstName:String?=""
    private  var lastName:String?=""


    override fun initView(mViewDataBinding: ViewDataBinding?) {
        activityRequestAcceptBinding= mViewDataBinding as ActivityRequestAcceptBinding
        orderDetailViewModel=ViewModelProviders.of(this).get(OrderDetailViewModel::class.java)
        activityRequestAcceptBinding.orderDetailViewModel=orderDetailViewModel
        activityRequestAcceptBinding.setLifecycleOwner(this)
        orderDetailViewModel.navigator=this
        setSupportActionBar(activityRequestAcceptBinding.tlOrderDetail.tl_header)
        context=this
        getIntentValues()
        updateViews()
        getObserValues()
    }

    fun getObserValues(){
        orderDetailViewModel.preparationTime.observe(this,Observer<String>{
            if(!it.isNullOrEmpty()){
                orderDetailViewModel.acceptOrder()
            }
        })

        orderDetailViewModel.commonRespnoseLd.observe(this, Observer<CommonSuccessResponse> {
            if(it.statusCode.equals("200")){
                commanMethods.showToast(context,it.message!!,true)
                finish()
            }
        })


        orderDetailViewModel.loadingProgressLd.observe(this, Observer {
            baseLiveDataLoading.value=it
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_request_accept
    }

    fun getIntentValues(){
        val newOrderTypeToken = object : TypeToken<NewOrderModel.ResponseData>() {}.type
        val strOrderModel=if(intent!=null && intent.hasExtra("orderDetail")) intent.getStringExtra("orderDetail") else ""
        if(!strOrderModel.isNullOrEmpty())
        orderDetailViewModel.incomingOrderModelLd.value = Gson().fromJson<NewOrderModel.ResponseData>(strOrderModel!!,newOrderTypeToken)

    }

    fun updateViews(){
        activityRequestAcceptBinding.disputeBtn.visibility=View.GONE
        if(orderDetailViewModel.incomingOrderModelLd.value!=null){
            if(orderDetailViewModel.incomingOrderModelLd.value!!.user!!.picture!=null)
            imageUtils.loadProfileImage(context,activityRequestAcceptBinding.ivOrderUserImage,orderDetailViewModel.incomingOrderModelLd.value!!.user!!.picture!!)

            if(orderDetailViewModel.incomingOrderModelLd.value!!.delivery!!.street!=null)
            activityRequestAcceptBinding.tvOrderUserAdd.setText(orderDetailViewModel.incomingOrderModelLd.value!!.delivery!!.street)
            activityRequestAcceptBinding.tvOrderPayMode.setText(orderDetailViewModel.incomingOrderModelLd.value!!.invoice!!.payment_mode)

            firstName=orderDetailViewModel.incomingOrderModelLd.value!!.user!!.first_name?:""
            lastName=orderDetailViewModel.incomingOrderModelLd.value!!.user!!.last_name?:""
            activityRequestAcceptBinding.tvOrderUserName.setText(firstName+" "+lastName)

            activityRequestAcceptBinding.orderAcceptLay.visibility= View.VISIBLE
            orderDetailViewModel.storeID.value=orderDetailViewModel.incomingOrderModelLd.value!!.id.toString()
            orderDetailViewModel.userID.value=orderDetailViewModel.incomingOrderModelLd.value!!.user_id.toString()

            activityRequestAcceptBinding.layInvoice.tvOrderSubTotal.setText(commanMethods.getNumberFormat()!!.format(orderDetailViewModel.incomingOrderModelLd.value!!.invoice!!.item_price))
            activityRequestAcceptBinding.layInvoice.tvOrderDeliveryCharge.setText(commanMethods.getNumberFormat()!!.format(orderDetailViewModel.incomingOrderModelLd.value!!.invoice!!.delivery_amount))
            activityRequestAcceptBinding.layInvoice.tvOrderPromoAmt.setText(commanMethods.getNumberFormat()!!.format(orderDetailViewModel.incomingOrderModelLd.value!!.invoice!!.promocode_amount))
            activityRequestAcceptBinding.layInvoice.tvOrderShopDiscount.setText(commanMethods.getNumberFormat()!!.format(orderDetailViewModel.incomingOrderModelLd.value!!.invoice!!.discount))
            activityRequestAcceptBinding.layInvoice.tvOrderTotal.setText(commanMethods.getNumberFormat()!!.format(orderDetailViewModel.incomingOrderModelLd.value!!.invoice!!.payable))
            val orderListAdapter=OrderListAdapter(this,orderDetailViewModel.incomingOrderModelLd.value!!.invoice!!.items!!)
            activityRequestAcceptBinding.rvOrderDetail.setAdapter(orderListAdapter)
        }
    }

    override fun getErrorMessage(message: String) {
        commanMethods.showToast(context,message,false)
    }

    override fun acceptOrder() {
        val tollChargeDialog =AcceptOrderFragment()
        tollChargeDialog.show(supportFragmentManager, "preparetTime")
    }

    override fun cancelOrder() {
        orderDetailViewModel.cancelOrder()
    }

}