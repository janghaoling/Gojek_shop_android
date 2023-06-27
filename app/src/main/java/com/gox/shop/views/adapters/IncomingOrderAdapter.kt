package com.gox.shop.views.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.gox.shop.R
import com.gox.shop.application.AppController
import com.gox.shop.databinding.RowIncomingOrdersBinding
import com.gox.shop.datamodel.NewOrderModel
import com.gox.shop.utils.CommanMethods
import com.gox.shop.utils.ImageUtils
import com.gox.shop.views.orderdetail.OrderDetailActivity
import com.gox.shop.views.orders.incoming.IncomingViewModel
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

class  IncomingOrderAdapter(mViewModel:IncomingViewModel,contxt: Context,newOrderModel:NewOrderModel):RecyclerView.Adapter<IncomingOrderAdapter.IncomingOrderViewModel>(){
    @Inject
      lateinit var  imageUtils: ImageUtils
    @Inject
      lateinit var  commanMethods: CommanMethods

    private  var mViewModel:IncomingViewModel?=null
    private  var ctxt:Context?=null
    private  var incomingOrderModel:NewOrderModel?=null

    init {
        this.mViewModel=mViewModel
        this.ctxt=contxt
        this.incomingOrderModel=newOrderModel
        AppController.appComponent.inject(this)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomingOrderViewModel {
        val incomingViewModel=IncomingOrderViewModel(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.row_incoming_orders,parent,false))
        return incomingViewModel
    }

    override fun getItemCount(): Int {
        return incomingOrderModel!!.responseData!!.size
    }

    override fun onBindViewHolder(holder: IncomingOrderViewModel, position: Int) {
        holder.incomingOrderBinding.address.setText(incomingOrderModel!!.responseData!!.get(position)!!.delivery!!.map_address)
        imageUtils.loadProfileImage(ctxt!!,holder.incomingOrderBinding.userImg,incomingOrderModel!!.responseData!!.get(position)!!.user!!.picture.toString())
        holder.incomingOrderBinding.userName.setText(incomingOrderModel!!.responseData!!.get(position)!!.user!!.first_name)
        holder.incomingOrderBinding.paymentMode.setText(incomingOrderModel!!.responseData!!.get(position)!!.invoice!!.payment_mode)

        holder.incomingOrderBinding.itemLayout.setOnClickListener(View.OnClickListener {
            val intent= Intent(ctxt,OrderDetailActivity::class.java)
            val strOrderModel=Gson().toJson(incomingOrderModel!!.responseData!!.get(position))
            intent.putExtra("orderDetail",strOrderModel)
            ctxt!!.startActivity(intent)
        })
    }

    inner  class  IncomingOrderViewModel(itemView:RowIncomingOrdersBinding):RecyclerView.ViewHolder(itemView.root){
        val incomingOrderBinding:RowIncomingOrdersBinding=itemView
    }

}