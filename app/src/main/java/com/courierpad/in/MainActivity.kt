package com.courierpad.`in`

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userNameEditText:EditText = findViewById(R.id.user_name_edit_text)
        val passwordEditText:EditText = findViewById(R.id.password_edit_text)
        val loginBtn:Button = findViewById(R.id.login_btn)

        loginBtn.setOnClickListener{
            val username:String = userNameEditText.text.toString()
            val password:String = passwordEditText.text.toString()

            if(username == "biswajit"){
                if(password == "123"){
                    val adminHomePage:Intent = Intent(applicationContext,Admin_Home::class.java)
                    startActivity(adminHomePage);
                }
                else
                    Toast.makeText(applicationContext, "Wrong Credential", Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(applicationContext, "Wrong Credential", Toast.LENGTH_SHORT).show()
        }
    }
}