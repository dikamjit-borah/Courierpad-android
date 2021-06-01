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
import com.courierpad.`in`.Agent_Detail
import com.courierpad.`in`.Order_detail
import com.courierpad.`in`.R
import com.courierpad.`in`.models.AgentModel
import org.w3c.dom.Text
import java.lang.Exception

class AgentAdapter(context: Context,agents: ArrayList<AgentModel>) :RecyclerView.Adapter<AgentAdapter.AgentViewHolder>() {

    lateinit var agentsArraylist:ArrayList<AgentModel>
    lateinit var context: Context;

    init {
        agentsArraylist = agents;
        this.context = context;

    }
    inner class AgentViewHolder(itemview:View):RecyclerView.ViewHolder(itemview)
    {
        val agentId:TextView= itemview.findViewById(R.id.view_agents_agent_id)
        val agentStatus:TextView = itemview.findViewById(R.id.view_agents_agent_status)
        val agentName:TextView = itemview.findViewById(R.id.view_agents_agent_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgentViewHolder {
        val view:View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_agent,parent, false)
        return AgentViewHolder(view);
    }

    override fun getItemCount(): Int {
        return agentsArraylist.size
    }

    override fun onBindViewHolder(holder: AgentViewHolder, position: Int) {
        val AgentId:String= ""+agentsArraylist[position].agent_id
    //  val agentStatus:String= ""+agentsArraylist[position].
        val AgentName:String= ""+agentsArraylist[position].agent_name
        val AgentDob:String= ""+agentsArraylist[position].agent_dob
        val AgentDoj:String= ""+agentsArraylist[position].agent_doj
        val AgentPhone:String= ""+agentsArraylist[position].agent_phone


        holder.agentId.setText("#"+AgentId)
        holder.agentName.setText(AgentName)

//        if(agentsArraylist[position].agent_status == "Completed")
//            holder.orderStatus.setBackgroundResource(R.drawable.status_card_completed)
//        else
//            holder.orderStatus.setBackgroundResource(R.drawable.status_card_pending)


        holder.itemView.setOnClickListener{
            val agentDetailIntent:Intent = Intent(context, Agent_Detail::class.java)
            agentDetailIntent.putExtra("agentId",AgentId)
            agentDetailIntent.putExtra("agentName",AgentName)
            agentDetailIntent.putExtra("agentDob",AgentDob)
            agentDetailIntent.putExtra("agentDoj",AgentDoj)
            agentDetailIntent.putExtra("agentPhone",AgentPhone)

            context.startActivity(agentDetailIntent)
        }

    }
}