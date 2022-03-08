package com.courierpad.`in`

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.courierpad.`in`.adapters.OrdersAdapter
import com.courierpad.`in`.apiHandling.ApiInterface
import com.courierpad.`in`.apiHandling.SessionManager
import com.courierpad.`in`.models.OrdersModel
import com.courierpad.`in`.utilities.CONSTANTS
import com.courierpad.`in`.utilities.LoadingDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.typeOf


class Unassigned_Orders : AppCompatActivity() {
    lateinit var orderRecyclerView: RecyclerView
    private lateinit var loading: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view__orders)

        CONSTANTS.isAssignedOrder = 0
        loading = LoadingDialog(this)
        val orders:ArrayList<OrdersModel> = arrayListOf()
//        val order1:OrdersModel = OrdersModel(1234,"Pending")
//        val order2:OrdersModel = OrdersModel(1234534,"Completed")
//        val order3:OrdersModel = OrdersModel(13452434,"Pending")
        loading.startLoading()
        getDataFromApi(orders,this)




//        orders.add(order1)
//        orders.add(order2)
//        orders.add(order3)
        orderRecyclerView= findViewById(R.id.order_recycler_view)

    }

    private fun getDataFromApi(orders:ArrayList<OrdersModel>,context: Context) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://192.168.29.21:3000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getAllOrders(0)

        retrofitData.enqueue(object : Callback<List<OrdersModel>?> {

            override fun onResponse(
                call: Call<List<OrdersModel>?>,
                response: Response<List<OrdersModel>?>
            ) {
                loading.Dismiss()
                val responseBody = response.body()
                // Toast.makeText(applicationContext,"res" + responseBody!!.get(0).order_client,Toast.LENGTH_SHORT).show()

                for (item in responseBody!!){
                    //Toast.makeText(applicationContext,"test",Toast.LENGTH_SHORT).show()
                    val order:OrdersModel = OrdersModel(item.order_id, item.order_date, item.order_client, item.order_status, item.order_receiver, item.order_location, item.order_phone)
                    orders.add(order)
                }
                val orderAdapter:OrdersAdapter= OrdersAdapter( context,orders)

                orderRecyclerView.adapter = orderAdapter

                val linearLayoutManger = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
                orderRecyclerView.layoutManager = linearLayoutManger



            }
            override fun onFailure(call: Call<List<OrdersModel>?>, t: Throwable) {
                Toast.makeText(applicationContext," Error "+t.message,Toast.LENGTH_SHORT).show()
            }
        })
    }
}