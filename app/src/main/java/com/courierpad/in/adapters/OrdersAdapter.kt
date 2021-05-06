package com.courierpad.`in`.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.courierpad.`in`.Order_detail
import com.courierpad.`in`.R
import com.courierpad.`in`.models.OrdersModel
import org.w3c.dom.Text
import java.lang.Exception

class OrdersAdapter(context: Context,orders: ArrayList<OrdersModel>) :RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    lateinit var ordersArraylist:ArrayList<OrdersModel>
    lateinit var context: Context;

    init {
        ordersArraylist = orders;
        this.context = context;

    }
    inner class OrderViewHolder(itemview:View):RecyclerView.ViewHolder(itemview)
    {
        val orderId:TextView= itemview.findViewById(R.id.order_id)
        val orderStatus:TextView = itemview.findViewById(R.id.order_status)
        val clientName:TextView = itemview.findViewById(R.id.order_card_username)
        val orderDate:TextView = itemview.findViewById(R.id.order_card_date)
        val orderCard:CardView = itemview.findViewById(R.id.order_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
       val view:View = LayoutInflater.from(parent.context)
           .inflate(R.layout.card_order,parent, false)
        return OrderViewHolder(view);
    }

    override fun getItemCount(): Int {
        return ordersArraylist.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val OrderID:String= ""+ordersArraylist[position].order_id
        val OrderStatus:String= ""+ordersArraylist[position].order_status
        val ClientName:String= ""+ordersArraylist[position].order_client
        var OrderDate:String= ""+ordersArraylist[position].order_date

        var OrderReciever:String= ""+ordersArraylist[position].order_receiver
        var OrderLocation:String= ""+ordersArraylist[position].order_location
        var OrderPhone:String= ""+ordersArraylist[position].order_phone


        holder.orderId.setText("# "+OrderID)
        holder.orderStatus.setText(OrderStatus)
        holder.clientName.setText(ClientName)
        OrderDate = OrderDate.replaceAfterLast("T", "")
        holder.orderDate.setText(OrderDate.replace("T", ""))

        if(ordersArraylist[position].order_status == "Completed")
            holder.orderStatus.setBackgroundResource(R.drawable.status_card_completed)
        else
            holder.orderStatus.setBackgroundResource(R.drawable.status_card_pending)

        holder.itemView.setOnClickListener{
            val orderDetailIntent:Intent = Intent(context, Order_detail::class.java)
            orderDetailIntent.putExtra("orderId",OrderID)
            orderDetailIntent.putExtra("orderStatus",OrderStatus)
            orderDetailIntent.putExtra("clientName",ClientName)
            orderDetailIntent.putExtra("orderDate",OrderDate)
            orderDetailIntent.putExtra("orderReceiver",OrderReciever)
            orderDetailIntent.putExtra("orderLocation",OrderLocation)
            orderDetailIntent.putExtra("orderPhone",OrderPhone)
            context.startActivity(orderDetailIntent)
        }

    }
}