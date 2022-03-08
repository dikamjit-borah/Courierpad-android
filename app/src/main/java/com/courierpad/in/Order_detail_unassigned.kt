package com.courierpad.`in`

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.courierpad.`in`.apiHandling.ApiInterface
import com.courierpad.`in`.apiHandling.SessionManager
import com.courierpad.`in`.models.AgentModel
import com.courierpad.`in`.utilities.CONSTANTS
import com.courierpad.`in`.utilities.LoadingDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import com.google.gson.JsonObject
import java.text.SimpleDateFormat
import java.util.*


class Order_detail_unassigned : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    private lateinit var orderStatusView: TextView
    private lateinit var bComplete:Button
    lateinit var jsonObject: JsonObject
    lateinit var availableAgents:MutableList<String>
    private lateinit var selectedAgent:String
    private lateinit var sessionManager: SessionManager
    private lateinit var loading: LoadingDialog
    var OrderID:Int = 0;
    lateinit var etEta:EditText
    lateinit var cal:Calendar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail_unassigned)
        loading = LoadingDialog(this@Order_detail_unassigned)

        jsonObject = JsonObject()
        availableAgents = arrayListOf()

        OrderID =   intent.getStringExtra("orderId")!!.toInt()
        val OrderStatus:String = intent.getStringExtra("orderStatus").toString()
        val ClientName:String = intent.getStringExtra("clientName").toString()
        val ReceiverName:String = intent.getStringExtra("orderReceiver").toString()
        val OrderPhone:String = intent.getStringExtra("orderPhone").toString()
        val OrderLocation:String = intent.getStringExtra("orderLocation").toString()
        val OrderDate:String = intent.getStringExtra("orderDate").toString()
        orderStatusView = findViewById<TextView>(R.id.order_detail_status)


        findViewById<TextView>(R.id.order_detail_id).setText("# "+OrderID)

        orderStatusView.setText("Unassigned")
        findViewById<TextView>(R.id.order_detail_client_name).setText(ClientName)
        findViewById<TextView>(R.id.order_detail_receiver_name).setText(ReceiverName)
        findViewById<TextView>(R.id.order_detail_order_address).setText(OrderLocation)
        findViewById<TextView>(R.id.order_detail_phone).setText(OrderPhone)

        bComplete = findViewById(R.id.bComplete);

        bComplete.setOnClickListener {
            updateStatus()
        }
         cal = Calendar.getInstance()

         etEta = findViewById<EditText>(R.id.etEta)

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
                DatePickerDialog(this@Order_detail_unassigned,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })

        getDataFromApi()

    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        etEta.setText(sdf.format(cal.getTime()))
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        Toast.makeText(applicationContext, "Nothing Selected",Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedAgent = availableAgents[position]
    }



    private fun updateStatus() {
        loading.startLoading()
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://192.168.29.109:3000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        jsonObject = JsonObject()
        jsonObject.addProperty("assigned_agent_id", selectedAgent.replaceAfter(" ","").replace("#","").replace(" ", ""))
        jsonObject.addProperty("order_id", OrderID);
        jsonObject.addProperty("order_estimated_date", "2022-02-08")

        Log.d("TAG", "getDataFromApi:totototo "+jsonObject)

        val retrofitData = retrofitBuilder.addPartialBody(jsonObject)

        retrofitData.enqueue(object : Callback<Array<Int>> {
            override fun onResponse(call: Call<Array<Int>>, response: Response<Array<Int>>) {
                Toast.makeText(applicationContext, "Order successfully assigned.",Toast.LENGTH_LONG).show()
                startActivity(Intent(applicationContext,OrdersActivity::class.java))
                finish()
            }

            override fun onFailure(call: Call<Array<Int>>, t: Throwable) {
                Toast.makeText(applicationContext, "ERROR: "+t.message,Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getDataFromApi() {

        loading.startLoading()
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://192.168.29.109:3000/api/")
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
                spinner.onItemSelectedListener = this@Order_detail_unassigned

            }
            override fun onFailure(call: Call<List<AgentModel>?>, t: Throwable) {
                Toast.makeText(applicationContext," Error "+t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}