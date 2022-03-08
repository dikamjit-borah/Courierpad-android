package com.courierpad.`in`

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.courierpad.`in`.apiHandling.ApiInterface
import com.courierpad.`in`.models.AgentModel
import com.courierpad.`in`.utilities.LoadingDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Add_Agent : AppCompatActivity() {
    private lateinit var loading: LoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__agent)
        loading = LoadingDialog(this)

        findViewById<AppCompatButton>(R.id.add_agent_btn).setOnClickListener{


            val agentId:Int = findViewById<TextView>(R.id.add_agent_id).text.toString().toInt()
            val agentName:String = findViewById<TextView>(R.id.add_agent_name).text.toString()
            val agentDob:String = findViewById<TextView>(R.id.add_agent_dob).text.toString()
            val agentPhone:String = findViewById<TextView>(R.id.add_agent_phone).text.toString()
            val agentDoj:String = findViewById<TextView>(R.id.add_agent_doj).text.toString()

            val agentUserName:String = findViewById<TextView>(R.id.add_agent_username).text.toString()
            val agentPassword:String = findViewById<TextView>(R.id.add_agent_password).text.toString()
            val agentEmail:String = findViewById<EditText>(R.id.tvEmail).text.toString()
            val agentDataModelObj:AgentModel = AgentModel(agentId,agentName,agentDob,agentDoj,agentPhone,agentUserName,agentPassword, agentEmail)

            postDataToApi(agentDataModelObj)

        }
    }


    private fun postDataToApi(agentDataModelObj: AgentModel) {
        loading.startLoading()
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://192.168.29.21:3000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.addAgent(agentDataModelObj)

        retrofitData.enqueue(object : Callback<AgentModel> {

            override fun onResponse(
                call: Call<AgentModel>?,
                response: Response<AgentModel>?

            ) {
                // Toast.makeText(applicationContext,"res" + responseBody!!.get(0).order_client,Toast.LENGTH_SHORT).show()
                loading.Dismiss()
                Toast.makeText(applicationContext,"Agent added", Toast.LENGTH_SHORT).show()


            }
            override fun onFailure(call: Call<AgentModel>?, t: Throwable) {
                Toast.makeText(applicationContext," Error "+t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}