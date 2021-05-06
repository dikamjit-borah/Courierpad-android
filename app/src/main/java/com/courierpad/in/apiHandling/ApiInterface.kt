package com.courierpad.`in`.apiHandling

import com.courierpad.`in`.models.AgentModel
import retrofit2.Call
import com.courierpad.`in`.models.OrdersModel
import retrofit2.http.*

interface ApiInterface {

    @GET("admin/view_orders")
    fun getAllOrders(): Call<List<OrdersModel>>


    //Agents
    @GET("admin/view_agents")
    fun getAllAgents(): Call<List<AgentModel>>

    @GET("admin/available_agents")
    fun getAllAvailableAgents(): Call<List<AgentModel>>

    @Headers("Content-Type: application/json")
    @POST("admin/add_agent")
    fun addAgent(@Body agentData:AgentModel): Call<AgentModel>
}