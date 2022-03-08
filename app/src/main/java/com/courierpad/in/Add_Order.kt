package com.courierpad.`in`

import android.app.DatePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
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
import java.util.*
import kotlin.collections.ArrayList

class Add_Order : AppCompatActivity() , AdapterView.OnItemSelectedListener{
    private lateinit  var cal: Calendar
    private lateinit  var etEta: EditText
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
        selectedAgent = ""

        getDataFromApi()
        val btn =  findViewById<AppCompatButton>(R.id.add_order_btn)
       btn.setOnClickListener{
//           Log.e("add_order", ""+ selectedAgent)


            val orderIdx:String = findViewById<EditText>(R.id.add_order_order_id).text.toString()
            val clientName:String = findViewById<EditText>(R.id.add_order_client_name).text.toString()
            val deliveryAddress:String = findViewById<EditText>(R.id.add_order_delivery_address).text.toString()
            val receiverName:String = findViewById<EditText>(R.id.add_order_receiver_name).text.toString()
            val phoneNo:String = findViewById<EditText>(R.id.add_order_phone_no).text.toString()
           val email:String = findViewById<EditText>(R.id.tvEmail).text.toString()
//            val orderDate:String = LocalDate.now().toString()
           val date = Date()
           val orderDate: String = SimpleDateFormat("yyyy-MM-dd").format(date)
            val orderStatus:String = "IN TRANSIT"
            var agentId = selectedAgent.replaceAfter(" ","").replace("#","").replace(" ", "")
           if (agentId.isNullOrBlank())
           {
               Toast.makeText(applicationContext, "Please select an agent", Toast.LENGTH_SHORT).show();
               return@setOnClickListener
           }


           Log.d("ddasdd", orderIdx)

           val orderId = orderIdx.toInt()
           val agentIdint = agentId.toInt()

            val orderDataModelObj:OrdersModel = OrdersModel(orderId,orderDate,clientName,orderStatus,receiverName,deliveryAddress,phoneNo,email, agentIdint, etEta!!.text.toString())
           Toast.makeText(applicationContext, "Pleaent"+orderDataModelObj.order_email, Toast.LENGTH_SHORT).show();
           postDataToApi(orderDataModelObj)

        }
        sessionManager = SessionManager(this)
        availableAgents= ArrayList()
        etEta = findViewById<EditText>(R.id.etEta)
        cal = Calendar.getInstance()

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        etEta!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@Add_Order,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })

    }
    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = java.text.SimpleDateFormat(myFormat, Locale.US)
        etEta.setText(sdf.format(cal.getTime()))
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        Toast.makeText(applicationContext, "Nothing Selected",Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedAgent = availableAgents[position]
    }

    private fun getDataFromApi() {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://192.168.29.21:3000/api/")
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
        Log.d("ddasdd", orderDataModelObj.order_email)
        loading.startLoading()
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://192.168.29.21:3000/api/")
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