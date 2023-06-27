package com.gox.shop.views.orders.incoming

import android.app.Activity
import android.content.Context
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gox.shop.R
import com.gox.shop.base.BaseFragment
import com.gox.shop.databinding.FragmentIncomingOrdersBinding
import com.gox.shop.datamodel.NewOrderModel
import com.gox.shop.socket.SocketManager
import com.gox.shop.utils.Constants
import com.gox.shop.views.adapters.IncomingOrderAdapter
import com.gox.shop.views.dashboard.DashboardNavigator
import io.socket.emitter.Emitter

class IncomingFragment : BaseFragment<FragmentIncomingOrdersBinding>(), IncomingNavigator {
    private lateinit var fragmentIncomingOrdersBinding: FragmentIncomingOrdersBinding
    private lateinit var incomingViewModel: IncomingViewModel
    private lateinit var dashboardNavigator: DashboardNavigator
    private lateinit var ctx: Context

    override fun getLayoutId(): Int {
        return R.layout.fragment_incoming_orders
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dashboardNavigator = context as DashboardNavigator
    }

    override fun initView(mRootView: View?, mViewDataBinding: ViewDataBinding?) {
        fragmentIncomingOrdersBinding = mViewDataBinding as FragmentIncomingOrdersBinding
        incomingViewModel = ViewModelProviders.of(this).get(IncomingViewModel::class.java)
        fragmentIncomingOrdersBinding.incomingViewModel = incomingViewModel
        fragmentIncomingOrdersBinding.setLifecycleOwner(this)
        incomingViewModel.navigator = this
        if (dashboardNavigator != null)
            dashboardNavigator.setTitle(resources.getString(R.string.title_home))
        dashboardNavigator.hideLeftIcon(true)
        dashboardNavigator.hideRightIcon(true)
        ctx = activity!!
        getApiResponse()
        incomingViewModel.getIncomingOrders()
        SocketManager.emit(Constants.RoomName.JOINSHOPROOM, Constants.RoomID.TRANSPORT_ROOM)

        SocketManager.onEvent(Constants.RoomName.NEW_REQ, Emitter.Listener {
            Log.e("SOCKET", "SOCKET_SK transport STATUS ")
            incomingViewModel.getIncomingOrders()
        })
    }

    override fun onResume() {
        super.onResume()
        incomingViewModel.getIncomingOrders()
    }

    fun getApiResponse() {
        incomingViewModel.newOrderLiveData.observe(this, Observer<NewOrderModel> {
            if (it != null && it.statusCode.equals("200")) {
                if (!it.responseData.isNullOrEmpty()) {
                    fragmentIncomingOrdersBinding.rvIncomingOrders.visibility=View.VISIBLE
                    val incomingOrderAdapter = IncomingOrderAdapter(incomingViewModel, ctx!!, it)
                    fragmentIncomingOrdersBinding.rvIncomingOrders.setAdapter(incomingOrderAdapter)
                    fragmentIncomingOrdersBinding.rvIncomingOrders.adapter!!.notifyDataSetChanged()
                    fragmentIncomingOrdersBinding.emptyViewLayout.visibility = View.GONE

                } else {
                    fragmentIncomingOrdersBinding.rvIncomingOrders.visibility=View.GONE
                    fragmentIncomingOrdersBinding.emptyViewLayout.visibility = View.VISIBLE
                }
            }
        })

    }

    override fun showError(message: String) {
    }

}