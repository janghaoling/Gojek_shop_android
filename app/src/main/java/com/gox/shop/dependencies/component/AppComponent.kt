package com.gox.shop.dependencies.component

import androidx.databinding.ViewDataBinding
import com.gox.app.ui.changepasswordactivity.ChangePasswordActivity
import com.gox.app.ui.changepasswordactivity.ChangePasswordViewModel
import com.gox.shop.base.BaseActivity
import com.gox.shop.base.BaseRepository
import com.gox.shop.dependencies.modules.AppContainerModule
import com.gox.shop.dependencies.modules.ApplicationModule
import com.gox.shop.dependencies.modules.NetworkModule
import com.gox.shop.repository.ShopRepository
import com.gox.shop.utils.*
import com.gox.shop.views.accept.AcceptOrderFragment
import com.gox.shop.views.adapters.IncomingOrderAdapter
import com.gox.shop.views.adapters.OrderListAdapter
import com.gox.shop.views.dashboard.DashboardActivity
import com.gox.shop.views.dashboard.DashboardViewModel
import com.gox.shop.views.login.LoginActivity
import com.gox.shop.views.login.LoginViewModel
import com.gox.shop.views.orderdetail.OrderDetailActivity
import com.gox.shop.views.orderdetail.OrderDetailViewModel
import com.gox.shop.views.orders.incoming.IncomingViewModel
import com.gox.shop.views.splash.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NetworkModule::class, ApplicationModule::class, AppContainerModule::class))
interface AppComponent {

   fun inject(commonMethods: CommanMethods)

   fun inject(dateTimeUtility: DateTimeUtility)

   fun inject(splashActivity: SplashActivity)

   fun inject(baseRepository: BaseRepository)

   fun inject(shopRepository: ShopRepository)

   fun inject(sessionManager: SessionManager)

   fun inject(imageUtils: ImageUtils)

   fun inject(constants: Constants)

   //Adapters
   fun inject(incomingOrderAdapter: IncomingOrderAdapter)

   fun inject(orderListAdapter: OrderListAdapter)

   //Activity
   fun inject(loginActivity: LoginActivity)

   fun inject(dashboardActivity: DashboardActivity)

   fun inject(orderDetailActivity: OrderDetailActivity)

    fun inject(changePasswordActivity: ChangePasswordActivity)


   //ViewModel
   fun inject(loginViewModel: LoginViewModel)

   fun inject(incomingViewModel: IncomingViewModel)

   fun inject(dashboardViewModel: DashboardViewModel)

   fun inject(orderDetailViewModel: OrderDetailViewModel)

   fun inject(changePasswordViewModel: ChangePasswordViewModel)


   //Fragment
   fun inject(acceptOrderFragment: AcceptOrderFragment)
}