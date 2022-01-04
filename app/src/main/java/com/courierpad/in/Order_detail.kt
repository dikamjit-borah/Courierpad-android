package com.courierpad.`in`

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.courierpad.`in`.apiHandling.ApiInterface
import com.courierpad.`in`.utilities.CONSTANTS
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Order_detail : AppCompatActivity() {

    private lateinit var orderStatusView: TextView
    private lateinit var bComplete:Button
    lateinit var jsonObject:JsonObject
     var OrderID:Int = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        jsonObject = JsonObject()

        OrderID =   intent.getStringExtra("orderId")!!.toInt()
        val OrderStatus:String = intent.getStringExtra("orderStatus").toString()
        val ClientName:String = intent.getStringExtra("clientName").toString()
        val ReceiverName:String = intent.getStringExtra("orderReceiver").toString()
        val OrderPhone:String = intent.getStringExtra("orderPhone").toString()
        val OrderLocation:String = intent.getStringExtra("orderLocation").toString()
        val OrderDate:String = intent.getStringExtra("orderDate").toString()
        orderStatusView = findViewById<TextView>(R.id.order_detail_status)


        findViewById<TextView>(R.id.order_detail_id).setText("# "+OrderID)

        orderStatusView.setText(OrderStatus)
        findViewById<TextView>(R.id.order_detail_client_name).setText(ClientName)
        findViewById<TextView>(R.id.order_detail_receiver_name).setText(ReceiverName)
        findViewById<TextView>(R.id.order_detail_order_address).setText(OrderLocation)
        findViewById<TextView>(R.id.order_detail_phone).setText(OrderPhone)

        bComplete = findViewById(R.id.bComplete);

        if(OrderStatus.equals("COMPLETED")){
            orderStatusView.setBackgroundResource(R.drawable.status_card_completed)
            bComplete.visibility = View.GONE
        }


        bComplete.setOnClickListener {
            updateStatus()
        }


    }

    private fun updateStatus() {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://courierpad.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)


        Log.d("ihihihhi", "updateStatus: ororor"+OrderID)
        jsonObject.addProperty("order_id", OrderID);
        jsonObject.addProperty("agent_id", CONSTANTS.AGENT_ID)

        Log.d("bofy", "updateStatus: sirfisr"+jsonObject.toString())
        val retrofitData = retrofitBuilder.completeOrder(jsonObject)

        retrofitData.enqueue(object : Callback<JsonObject> {

            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                Log.d("asdads", "onResponse: "+ response.body())
                findViewById<TextView>(R.id.order_detail_status)
                orderStatusView.setBackgroundResource(R.drawable.status_card_completed)
                orderStatusView.setText("Order completed")
                Toast.makeText(applicationContext, "Order has been marked as complete", Toast.LENGTH_LONG).show();

            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(applicationContext," Error "+t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}