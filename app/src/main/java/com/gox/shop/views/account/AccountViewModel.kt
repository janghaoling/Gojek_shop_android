package com.gox.shop.views.account

import androidx.lifecycle.MutableLiveData
import com.gox.partner.views.account.AccountMenuAdapter
import com.gox.shop.base.BaseViewModel
import com.gox.shop.datamodel.AccountMenuModel
import com.gox.shop.datamodel.CommonSuccessResponse

class  AccountViewModel:BaseViewModel<AccountNavigator>(){
    private lateinit var adapter: AccountMenuAdapter
    private lateinit var accountMenus: List<AccountMenuModel>
    val successResponse = MutableLiveData<CommonSuccessResponse>()
    var errorResponse = MutableLiveData<String>()


    fun setAccountMenus(accountMenus: List<AccountMenuModel>) {
        this.accountMenus = accountMenus
    }

    fun setAdapter() {
        adapter = AccountMenuAdapter(this)
        adapter.notifyDataSetChanged()
    }

    fun getAdapter() = adapter

    fun getAccountMenus() = accountMenus

    fun getAccountMenu(position: Int) = accountMenus[position]

    fun onItemClick(position: Int) = navigator.onMenuItemClicked(position)

}