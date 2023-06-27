package com.gox.shop.views.login

interface  LoginNavigator {
  fun showError(errorMsg:String)
  fun validate():Boolean
  fun forgetPasswordFun()
}