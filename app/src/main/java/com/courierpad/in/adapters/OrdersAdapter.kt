package com.courierpad.`in`.adapters

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.courierpad.`in`.MyOrders
import com.courierpad.`in`.Order_detail
import com.courierpad.`in`.R
import com.courierpad.`in`.apiHandling.ApiInterface
import com.courierpad.`in`.models.OrdersModel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OrdersAdapter(context: Context, orders: ArrayList<OrdersModel>) :
    RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    lateinit var ordersArraylist: ArrayList<OrdersModel>
    lateinit var context: Context

    init {
        ordersArraylist = orders
        this.context = context

    }

    inner class OrderViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val orderId: TextView = itemview.findViewById(R.id.order_id)
        val orderStatus: TextView = itemview.findViewById(R.id.order_status)
        val clientName: TextView = itemview.findViewById(R.id.order_card_username)
        val orderDate: TextView = itemview.findViewById(R.id.order_card_date)
        val orderCard: CardView = itemview.findViewById(R.id.order_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ordersArraylist.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val OrderID: String = "" + ordersArraylist[position].order_id
        val OrderStatus: String = "" + ordersArraylist[position].order_status
        val ClientName: String = "" + ordersArraylist[position].order_client
        var OrderDate: String = "" + ordersArraylist[position].order_date

        var OrderReciever: String = "" + ordersArraylist[position].order_receiver
        var OrderLocation: String = "" + ordersArraylist[position].order_location
        var OrderPhone: String = "" + ordersArraylist[position].order_phone

        holder.orderCard.setOnLongClickListener {
            val builder = AlertDialog.Builder(context)
            //set title for alert dialog
            builder.setTitle("Update order")

            builder.setMessage("Mark order as complete?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            builder.setPositiveButton("Yes"){dialogInterface, which ->
                updateOrder(OrderID)
            }
            builder.create().show()

            return@setOnLongClickListener true
        }


        holder.orderId.text = "# " + OrderID
        holder.orderStatus.text = OrderStatus
        holder.clientName.text = ClientName
        OrderDate = OrderDate.replaceAfterLast("T", "")
        holder.orderDate.text = OrderDate.replace("T", "")

        if (ordersArraylist[position].order_status == "COMPLETED")
            holder.orderStatus.setBackgroundResource(R.drawable.status_card_completed)
        else
            holder.orderStatus.setBackgroundResource(R.drawable.status_card_pending)

        holder.itemView.setOnClickListener {
            val orderDetailIntent: Intent = Intent(context, Order_detail::class.java)
            orderDetailIntent.putExtra("orderId", OrderID)
            orderDetailIntent.putExtra("orderStatus", OrderStatus)
            orderDetailIntent.putExtra("clientName", ClientName)
            orderDetailIntent.putExtra("orderDate", OrderDate)
            orderDetailIntent.putExtra("orderReceiver", OrderReciever)
            orderDetailIntent.putExtra("orderLocation", OrderLocation)
            orderDetailIntent.putExtra("orderPhone", OrderPhone)
            context.startActivity(orderDetailIntent)
        }

    }

    private fun updateOrder(OrderID: String) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://courierpad.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.updateOrder(OrderID)

        retrofitData.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                //Toast.makeText(context, "Order marked as completed", Toast.LENGTH_SHORT).show()
                context.startActivity(Intent(context, MyOrders::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));



            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
               // Toast.makeText(context, "" + t.message, Toast.LENGTH_SHORT).show()
                context.startActivity(Intent(context, MyOrders::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));

            }
        })
    }
}