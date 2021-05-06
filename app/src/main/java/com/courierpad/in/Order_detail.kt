package com.courierpad.`in`

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class Order_detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)


        val OrderID:String = intent.getStringExtra("orderId").toString()
        val OrderStatus:String = intent.getStringExtra("orderStatus").toString()
        val ClientName:String = intent.getStringExtra("clientName").toString()
        val ReceiverName:String = intent.getStringExtra("orderReceiver").toString()
        val OrderPhone:String = intent.getStringExtra("orderPhone").toString()
        val OrderLocation:String = intent.getStringExtra("orderLocation").toString()
        val OrderDate:String = intent.getStringExtra("orderDate").toString()

        findViewById<TextView>(R.id.order_detail_id).setText("# "+OrderID)
        findViewById<TextView>(R.id.order_detail_status).setText(OrderStatus)
        findViewById<TextView>(R.id.order_detail_client_name).setText(ClientName)
        findViewById<TextView>(R.id.order_detail_receiver_name).setText(ReceiverName)
        findViewById<TextView>(R.id.order_detail_order_address).setText(OrderLocation)
        findViewById<TextView>(R.id.order_detail_phone).setText(OrderPhone)
    }
}