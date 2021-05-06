package com.courierpad.`in`

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.courierpad.`in`.adapters.AgentAdapter
import com.courierpad.`in`.apiHandling.ApiInterface
import com.courierpad.`in`.models.AgentModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Add_Order : AppCompatActivity() , AdapterView.OnItemSelectedListener{
    lateinit var availableAgents:MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__order)

//        var selectedAgentId:Int=null
        availableAgents= ArrayList()
        getDataFromApi()


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
                val responseBody = response.body()
                for (item in responseBody!!){
                    availableAgents.add(item.agent_name+"  #"+item.agent_id)
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

    override fun onNothingSelected(parent: AdapterView<*>) {
        Toast.makeText(applicationContext, "Nothing Selected",Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//            val selectedAgentId:Int = availableAgentIds[position]
//            Toast.makeText(applicationContext,selectedAgentId, Toast.LENGTH_SHORT).show()
    }


}