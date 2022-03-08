package com.courierpad.`in`.apiHandling

import com.courierpad.`in`.models.AgentModel
import retrofit2.Call
import com.courierpad.`in`.models.OrdersModel
import com.courierpad.`in`.models.UserModel
import com.google.gson.JsonObject
import retrofit2.http.*

interface ApiInterface {

    //Auth
    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(@Body userData:UserModel): Call<JsonObject>


    @GET("admin/view_orders")
    fun getAllOrders(@Query(value = "assigned", encoded = true) assigned:Int ): Call<List<OrdersModel>>

    @Headers("Content-Type: application/json")
    @POST("admin/add_order")
    fun addOrder(@Body orderData:OrdersModel): Call<OrdersModel>

    @GET("agent/history")
    fun getMyOrders(@Query(value = "agent_id", encoded = true) Id:String ): Call<List<OrdersModel>>


    //Agents
    @GET("admin/view_agents")
    fun getAllAgents(): Call<List<AgentModel>>

    @GET("admin/available_agents")
    fun getAllAvailableAgents(): Call<List<AgentModel>>

    @Headers("Content-Type: application/json")
    @POST("admin/add_agent")
    fun addAgent(@Body agentData:AgentModel): Call<AgentModel>

    @PUT("admin/order/{id}")
    fun updateOrder(@Path("id")  order_id:String) : Call<JsonObject>

    @POST("agent/updateOrder")
    fun completeOrder( @Body body: JsonObject):Call<JsonObject>

    @Headers("Content-Type: application/json")
    @POST("admin/partial_order")
    fun addPartialBody(@Body body:JsonObject): Call<Array<Int>>
}