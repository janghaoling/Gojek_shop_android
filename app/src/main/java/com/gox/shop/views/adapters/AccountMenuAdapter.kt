package com.gox.partner.views.account

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.gox.shop.BR
import com.gox.shop.R
import com.gox.shop.databinding.RowAccountMenuBinding
import com.gox.shop.views.account.AccountViewModel
import kotlinx.android.synthetic.main.row_account_menu.view.*

class AccountMenuAdapter( private val mViewModel: AccountViewModel) :
        RecyclerView.Adapter<AccountMenuAdapter.ViewHolder>() {






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(DataBindingUtil.inflate<RowAccountMenuBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.row_account_menu,
                    parent, false)
            )

    override fun getItemCount() = mViewModel.getAccountMenus().size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mViewModel, position)
    }

    class ViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        val vHolder=binding.root
        fun bind(accountViewModel: AccountViewModel, position: Int) {
            binding.setVariable(BR.accountViewModel, accountViewModel)
            binding.setVariable(BR.position, position)
            binding.executePendingBindings()
        }
    }
}