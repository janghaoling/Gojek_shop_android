package com.gox.shop.views.dashboard

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gox.shop.R
import com.gox.shop.application.AppController
import com.gox.shop.base.BaseActivity
import com.gox.shop.databinding.ActivityDashboardBinding
import com.gox.shop.datamodel.CommonSuccessResponse
import com.gox.shop.utils.CommanMethods
import com.gox.shop.utils.Constants
import com.gox.shop.utils.SessionManager
import com.gox.shop.views.account.AccountFragment
import com.gox.shop.views.login.LoginActivity
import com.gox.shop.views.orders.incoming.IncomingFragment
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.header_toolbar.view.*
import kotlinx.android.synthetic.main.layout_header.*
import javax.inject.Inject

class DashboardActivity : BaseActivity<ActivityDashboardBinding>(),
    FragmentManager.OnBackStackChangedListener,
    BottomNavigationView.OnNavigationItemSelectedListener, DashboardNavigator {

    @Inject
    lateinit var commanMethods: CommanMethods

    @Inject
    lateinit var  sessionManager: SessionManager

    init {
        AppController.appComponent.inject(this)
    }

    private var currentFragment: String? = ""
    private lateinit var activityDashboardBinding: ActivityDashboardBinding
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var context: Context
    private var isFragmentInBackstack: Boolean = false

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        activityDashboardBinding = mViewDataBinding as ActivityDashboardBinding
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        activityDashboardBinding.dashboardModel = dashboardViewModel
        activityDashboardBinding.setLifecycleOwner(this)
        setSupportActionBar(activityDashboardBinding.tlDashboard.tl_header)
        context = this
        activityDashboardBinding.bottomNavigation.setOnNavigationItemSelectedListener(this)
        supportFragmentManager.addOnBackStackChangedListener(this)

        activityDashboardBinding.bottomNavigation.setSelectedItemId(R.id.home_fragment);

        iv_right.setOnClickListener(View.OnClickListener {
            dashboardViewModel.logout()
        })
        getObserveValues()
    }

    fun getObserveValues() {
        dashboardViewModel.loadingLiveData.observe(this, Observer<Boolean> {
            baseLiveDataLoading.value = it
        })

        dashboardViewModel.logoutResPonseLiveData.observe(this, Observer<CommonSuccessResponse> {
            if (it.statusCode.equals("200")) {
                //Clear session values
                sessionManager.sharedPreferences.edit().clear().commit()

                commanMethods.showToast(context, "successfully logout", true)

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()

            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_dashboard
    }


    private fun addBackStack(
        isReplaceAll: Boolean,
        ft: FragmentTransaction,
        backStackName: String,
        callingFragment: Fragment
    ) {
        if (currentFragment.equals(backStackName)) return

        if (isReplaceAll) {
            if (replaceFragment(backStackName) == false) {
                ft.replace(R.id.main_container, callingFragment, backStackName)
                ft.addToBackStack(callingFragment::class.java.simpleName)
                ft.commit()
                return
            }
        } else {
            ft.replace(R.id.main_container, callingFragment, backStackName)
            ft.addToBackStack(callingFragment::class.java.simpleName)
            ft.commit()
        }
    }

    /**
     * Method called for replacing the fragment.
     */
    private fun replaceFragment(backStackName: String): Boolean {
        val manager: FragmentManager = supportFragmentManager
        isFragmentInBackstack = false
        val count: Int = manager.getBackStackEntryCount()
        if (count > 0) {
            for (i in 0 until count) {
                if (manager.getBackStackEntryAt(i) != null && !TextUtils.isEmpty(
                        manager.getBackStackEntryAt(i).getName()
                    ) && !TextUtils.isEmpty(backStackName)
                ) {
                    if (manager.getBackStackEntryAt(i).getName().equals(backStackName)) {
                        manager.popBackStackImmediate(backStackName, 0)
                        isFragmentInBackstack = true
                        break
                    }
                }
            }
        }
        return isFragmentInBackstack

    }

    fun setCurrentFrag(currentFrag: String) {
        currentFragment = currentFrag
    }

    override fun setTitle(title: String) {
        if (!title.isNullOrEmpty())
            tbr_title.setText(title)
    }

    override fun showLogo(isNeedShow: Boolean) {

    }

    override fun setRightIcon(rightIcon: Int) {

    }

    override fun hideRightIcon(isNeedHide: Boolean) {
        if (isNeedHide)
            iv_right.visibility = View.GONE
        else
            iv_right.visibility = View.VISIBLE


    }

    override fun hideLeftIcon(isNeedHide: Boolean) {
        if (isNeedHide)
            iv_left.visibility = View.GONE
        else
            iv_left.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val fragCount = supportFragmentManager.backStackEntryCount
        if (fragCount == 0) {
            finish()
        }
    }

    override fun onBackStackChanged() {
        var topFragment: Fragment? = getTopFragment()
        val bottomNavigationItemView = bottom_navigation
        bottomNavigationItemView.setOnNavigationItemSelectedListener(null)
        if (topFragment != null) {
            when (topFragment::class.java.simpleName) {
                IncomingFragment::class.java.simpleName -> {
                    bottomNavigationItemView.menu.getItem(0).isChecked = true
                }

                AccountFragment::class.java.simpleName -> {
                    bottomNavigationItemView.menu.getItem(1).isChecked = true
                }
            }
        }
        bottomNavigationItemView.setOnNavigationItemSelectedListener(this)
    }

    fun getTopFragment(): Fragment? {
        supportFragmentManager.run {
            return when (backStackEntryCount) {
                0 -> null
                else -> findFragmentByTag(getBackStackEntryAt(backStackEntryCount - 1).name)
            }
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home_fragment -> {
                val incomingFragment = IncomingFragment()
                val ft = supportFragmentManager.beginTransaction()
                addBackStack(
                    true,
                    ft,
                    incomingFragment::class.java.simpleName,
                    incomingFragment

                )
                true
            }


            R.id.myaccount_fragment -> {
                val accountFragment = AccountFragment()
                val ft = supportFragmentManager.beginTransaction()
                addBackStack(
                    true,
                    ft,
                    accountFragment::class.java.simpleName,
                    accountFragment

                )
                true
            }

            else -> false
        }
    }

}