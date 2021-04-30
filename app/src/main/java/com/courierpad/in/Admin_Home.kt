package com.courierpad.`in`

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView


class Admin_Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin__home)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(getResources().getColor(R.color.green))
        }

        val addOrderCard:CardView = findViewById(R.id.add_order_card)
        val viewOrder:CardView = findViewById(R.id.view_order_card)


        addOrderCard.setOnClickListener{
            val addOrderIntent:Intent= Intent(applicationContext,Add_Order::class.java)
            startActivity(addOrderIntent)
        }

        viewOrder.setOnClickListener{
            val ordersIntent:Intent= Intent(applicationContext,View_Orders::class.java)
            startActivity(ordersIntent)
        }
    }
}