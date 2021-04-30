package com.courierpad.`in`

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.courierpad.`in`.adapters.OrdersAdapter
import com.courierpad.`in`.apiHandling.ApiInterface
import com.courierpad.`in`.models.OrdersModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.typeOf


class View_Orders : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view__orders)
        val orders:ArrayList<OrdersModel> = arrayListOf()
//        val order1:OrdersModel = OrdersModel(1234,"Pending")
//        val order2:OrdersModel = OrdersModel(1234534,"Completed")
//        val order3:OrdersModel = OrdersModel(13452434,"Pending")
        getDataFromApi(orders)

//        orders.add(order1)
//        orders.add(order2)
//        orders.add(order3)
//        val orderRecyclerView: RecyclerView = findViewById(R.id.order_recycler_view)
//        val orderAdapter:OrdersAdapter= OrdersAdapter( this,orders)
//
//        orderRecyclerView.adapter = orderAdapter
//
//        val linearLayoutManger = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
//        orderRecyclerView.layoutManager = linearLayoutManger

    }

    private fun getDataFromApi(orders:ArrayList<OrdersModel>) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://192.168.29.207:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<OrdersModel>?> {
            override fun onFailure(call: Call<List<OrdersModel>?>, t: Throwable) {
                Toast.makeText(applicationContext," sfvdgdfgsfdg "+t.message,Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<OrdersModel>?>,
                response: Response<List<OrdersModel>?>
            ) {
               val responseBody = response.body()
                for (item in responseBody!!){
                    val order:OrdersModel = OrdersModel(item)
                   Toast.makeText(applicationContext, ""+ "jgjhg", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}