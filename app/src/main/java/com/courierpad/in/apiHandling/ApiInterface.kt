package com.courierpad.`in`.apiHandling

import retrofit2.Call
import com.courierpad.`in`.models.OrdersModel
import retrofit2.http.GET

interface ApiInterface {

    @GET("admin/view_orders")
    fun getData(): Call<List<OrdersModel>>
}