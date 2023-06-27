package com.gox.shop.views.account

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.gox.app.ui.changepasswordactivity.ChangePasswordActivity
import com.gox.shop.R
import com.gox.shop.base.BaseFragment
import com.gox.shop.databinding.FragmentAccountBinding
import com.gox.shop.datamodel.AccountMenuModel
import com.gox.shop.views.dashboard.DashboardNavigator

class AccountFragment:BaseFragment<FragmentAccountBinding>(),AccountNavigator {
    private lateinit var mBinding: FragmentAccountBinding
    private lateinit var mViewModel: AccountViewModel
    private lateinit var dashBoardNavigator: DashboardNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
       dashBoardNavigator = context as DashboardNavigator
    }

    override fun getLayoutId() = R.layout.fragment_account
    override fun initView(mRootView: View?, mViewDataBinding: ViewDataBinding?) {
        mViewModel =ViewModelProviders.of(this).get(AccountViewModel::class.java)
        mViewModel.navigator = this
        mBinding = mViewDataBinding as FragmentAccountBinding
        mBinding.lifecycleOwner = this
        mBinding.accountViewModel = mViewModel
        dashBoardNavigator.setTitle(resources.getString(R.string.title_account))
        dashBoardNavigator.setRightIcon(R.drawable.ic_logout)
        dashBoardNavigator.hideRightIcon(false)
        dashBoardNavigator.hideLeftIcon(true)
        //dashBoardNavigator.showLogo(false)

      /*  dashBoardNavigator.getInstance().iv_right.setOnClickListener {
            ViewUtils.showAlert(activity!!, getString(R.string.xjek_logout_alert), object : ViewUtils.ViewCallBack {
                override fun onPositiveButtonClick(dialog: DialogInterface) {
                    mViewModel.logoutApp()
                    dialog.dismiss()
                }

                override fun onNegativeButtonClick(dialog: DialogInterface) {
                    dialog.dismiss()
                }
            })
        }*/

        val accountMenuTitles = resources.getStringArray(R.array.title_account)
        val accountMenuIcons = resources.obtainTypedArray(R.array.icon_account)
        val accountMenus = List(accountMenuTitles.size) {
            AccountMenuModel(accountMenuTitles[it], accountMenuIcons.getResourceId(it, -1))
        }

        for(accountMenu:AccountMenuModel in accountMenus){
            Log.e("Icon","---"+accountMenu.resId+" -----"+accountMenu.text)

        }
        accountMenuIcons.recycle()
        mViewModel.setAccountMenus(accountMenus)
        mViewModel.setAdapter()
    }

    override fun onMenuItemClicked(position: Int) = when (position) {

        0 -> {
            val intent = Intent(activity,ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        1 -> {

        }

        2 -> {

        }

        else -> {

        }
    }
}