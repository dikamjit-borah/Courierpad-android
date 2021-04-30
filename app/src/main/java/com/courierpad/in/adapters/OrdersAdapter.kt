package com.courierpad.`in`.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.courierpad.`in`.Order_detail
import com.courierpad.`in`.R
import com.courierpad.`in`.models.OrdersModel
import org.w3c.dom.Text

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
        holder.orderId.setText("# "+ordersArraylist[position].order_id)
        holder.orderStatus.setText(""+ordersArraylist[position].order_status)
        holder.clientName.setText(""+ordersArraylist[position].order_client)
        holder.orderDate.setText(""+ordersArraylist[position].order_date.replaceAfterLast("T", ""))

        if(ordersArraylist[position].order_status == "Completed")
            holder.orderStatus.setBackgroundResource(R.drawable.status_card_completed)
        else
            holder.orderStatus.setBackgroundResource(R.drawable.status_card_pending)

        holder.itemView.setOnClickListener{
            val orderDetailIntent:Intent = Intent(context, Order_detail::class.java)
            context.startActivity(orderDetailIntent)
        }

    }
}