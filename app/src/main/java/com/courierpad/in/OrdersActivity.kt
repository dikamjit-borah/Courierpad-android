package com.courierpad.`in`

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout

class OrdersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        findViewById<LinearLayout>(R.id.llAssigned).setOnClickListener{
            startActivity(Intent(applicationContext,View_Orders::class.java))}

        findViewById<LinearLayout>(R.id.llUnassigned).setOnClickListener{
            startActivity(Intent(applicationContext,Unassigned_Orders::class.java))}
    }
}