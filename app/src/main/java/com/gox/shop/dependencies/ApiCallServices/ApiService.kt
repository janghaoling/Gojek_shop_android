package com.gox.shop.dependencies.ApiCallServices

import com.gox.shop.datamodel.CommonSuccessResponse
import com.gox.shop.datamodel.LoginModel
import com.gox.shop.datamodel.NewOrderModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("api/v1/shop/login")
    fun postLogin(@FieldMap params: HashMap<String, String>): Observable<LoginModel>

    @GET("api/v1/shop/dispatcher/orders")
    fun getIncomingOrders(@Query("type") type: String
                          ):Observable<NewOrderModel>


    @POST("api/v1/shop/logout")
    fun postLogout(): Observable<CommonSuccessResponse>

    @FormUrlEncoded
    @POST("api/v1/shop/dispatcher/accept")
    fun acceptOrder(@FieldMap params: HashMap<String, String>):Observable<CommonSuccessResponse>


    @FormUrlEncoded
    @POST("api/v1/shop/dispatcher/cancel")
    fun cancelOrder(@FieldMap params: HashMap<String, String>):Observable<CommonSuccessResponse>


    @FormUrlEncoded
    @POST("api/v1/shop/password")
    fun changePassword(@FieldMap params: HashMap<String, String>):Observable<CommonSuccessResponse>

}