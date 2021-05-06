package com.courierpad.`in`

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Agent_Detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent__detail)

        val AgentId:String = intent.getStringExtra("agentId").toString()
        val AgentName:String = intent.getStringExtra("agentName").toString()
        val AgentDob:String = intent.getStringExtra("agentDob").toString()
        val AgentDoj:String = intent.getStringExtra("agentDoj").toString()
        val AgentPhone:String = intent.getStringExtra("agentPhone").toString()


        findViewById<TextView>(R.id.agent_detail_id).setText("# "+AgentId)
        findViewById<TextView>(R.id.agent_detail_name).setText(AgentName)
        findViewById<TextView>(R.id.agent_detail_dob).setText(AgentDob)
        findViewById<TextView>(R.id.agent_detail_doj).setText(AgentDoj)
        findViewById<TextView>(R.id.agent_detail_phone).setText(AgentPhone)

    }
}