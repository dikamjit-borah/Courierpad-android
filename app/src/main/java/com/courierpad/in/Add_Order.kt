package com.courierpad.`in`

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.courierpad.`in`.adapters.AgentAdapter
import com.courierpad.`in`.apiHandling.ApiInterface
import com.courierpad.`in`.apiHandling.SessionManager
import com.courierpad.`in`.models.AgentModel
import com.courierpad.`in`.models.OrdersModel
import com.courierpad.`in`.utilities.LoadingDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

class Add_Order : AppCompatActivity() , AdapterView.OnItemSelectedListener{
    lateinit var availableAgents:MutableList<String>
    private lateinit var selectedAgent:String
    private lateinit var sessionManager: SessionManager
    private lateinit var loading: LoadingDialog

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__order)
        loading = LoadingDialog(this)
        loading.startLoading()

        getDataFromApi()
        val btn =  findViewById<AppCompatButton>(R.id.add_order_btn)
       btn.setOnClickListener{
//           Log.e("add_order", ""+ selectedAgent)
           loading.startLoading()

            val orderIdx:String = findViewById<EditText>(R.id.add_order_order_id).text.toString()
            val clientName:String = findViewById<EditText>(R.id.add_order_client_name).text.toString()
            val deliveryAddress:String = findViewById<EditText>(R.id.add_order_delivery_address).text.toString()
            val receiverName:String = findViewById<EditText>(R.id.add_order_receiver_name).text.toString()
            val phoneNo:String = findViewById<EditText>(R.id.add_order_phone_no).text.toString()

            val orderDate:String = LocalDate.now().toString()
            val orderStatus:String = "IN TRANSIT"
            val agentId = selectedAgent.replaceAfter(" ","").replace("#","").replace(" ", "")


           Log.e("ddasdd", orderIdx)

           val orderId = orderIdx.toInt()

            val orderDataModelObj:OrdersModel = OrdersModel(orderId,orderDate,clientName,orderStatus,receiverName,deliveryAddress,phoneNo,agentId.toInt())
            postDataToApi(orderDataModelObj)

        }
        sessionManager = SessionManager(this)
        availableAgents= ArrayList()

    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        Toast.makeText(applicationContext, "Nothing Selected",Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedAgent = availableAgents[position]
    }

    private fun getDataFromApi() {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://courierpad.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getAllAvailableAgents()

        retrofitData.enqueue(object : Callback<List<AgentModel>?> {

            override fun onResponse(
                call: Call<List<AgentModel>?>,
                response: Response<List<AgentModel>?>
            ) {
                loading.Dismiss()
                val responseBody = response.body()
                for (item in responseBody!!){
                    availableAgents.add("#"+item.agent_id + " "+item.agent_name)
                }

                val spinner:Spinner = findViewById(R.id.add_order_spinner)

                val arrayAdapter:ArrayAdapter<String> = ArrayAdapter(applicationContext, R.layout.spinner_text_view,availableAgents)
                spinner.adapter = arrayAdapter
                spinner.onItemSelectedListener = this@Add_Order

            }
            override fun onFailure(call: Call<List<AgentModel>?>, t: Throwable) {
                Toast.makeText(applicationContext," Error "+t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun postDataToApi(orderDataModelObj: OrdersModel) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://courierpad.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.addOrder(orderDataModelObj)

        retrofitData.enqueue(object : Callback<OrdersModel> {

            override fun onResponse(
                call: Call<OrdersModel>?,
                response: Response<OrdersModel>?

            ) {
                // Toast.makeText(applicationContext,"res" + responseBody!!.get(0).order_client,Toast.LENGTH_SHORT).show()
                loading.Dismiss()
                Toast.makeText(applicationContext,"Order added", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<OrdersModel>?, t: Throwable) {
                Toast.makeText(applicationContext," Error "+t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }



}