package com.courierpad.`in`

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.courierpad.`in`.adapters.AgentAdapter
import com.courierpad.`in`.adapters.OrdersAdapter
import com.courierpad.`in`.apiHandling.ApiInterface
import com.courierpad.`in`.models.AgentModel
import com.courierpad.`in`.models.OrdersModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class View_Agents : AppCompatActivity() {
    lateinit var agentRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view__agents)

        val agents:ArrayList<AgentModel> = arrayListOf()
//        val order1:OrdersModel = OrdersModel(1234,"Pending")
//        val order2:OrdersModel = OrdersModel(1234534,"Completed")
//        val order3:OrdersModel = OrdersModel(13452434,"Pending")
        getDataFromApi(agents,this)


//        orders.add(order1)
//        orders.add(order2)
//        orders.add(order3)
        agentRecyclerView= findViewById(R.id.view_agent_recyclerView)
    }

    private fun getDataFromApi(agents:ArrayList<AgentModel>,context: Context) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://courierpad.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getAllAgents()

        retrofitData.enqueue(object : Callback<List<AgentModel>?> {

            override fun onResponse(
                call: Call<List<AgentModel>?>,
                response: Response<List<AgentModel>?>
            ) {
                val responseBody = response.body()
                // Toast.makeText(applicationContext,"res" + responseBody!!.get(0).order_client,Toast.LENGTH_SHORT).show()

                for (item in responseBody!!){
                    val agent:AgentModel = AgentModel(item.agent_id, item.agent_name, item.agent_dob,item.agent_doj, item.agent_phone)
                    agents.add(agent)
                }
                val agentAdapter: AgentAdapter = AgentAdapter(context,agents)

                agentRecyclerView.adapter = agentAdapter

                val linearLayoutManger = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
                agentRecyclerView.layoutManager = linearLayoutManger
            }
            override fun onFailure(call: Call<List<AgentModel>?>, t: Throwable) {
                Toast.makeText(applicationContext," Error "+t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}