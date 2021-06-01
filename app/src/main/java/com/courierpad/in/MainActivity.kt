package com.courierpad.`in`

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.courierpad.`in`.apiHandling.ApiInterface
import com.courierpad.`in`.apiHandling.SessionManager
import com.courierpad.`in`.models.AgentModel
import com.courierpad.`in`.models.UserModel
import com.courierpad.`in`.utilities.LoadingDialog
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var selectedUserType: String
    private lateinit var loading:LoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userTypeRadioGroup:RadioGroup = findViewById(R.id.loginRadioGrp)
        val userNameEditText:EditText = findViewById(R.id.user_name_edit_text)
        val passwordEditText:EditText = findViewById(R.id.password_edit_text)
        val loginBtn:Button = findViewById(R.id.login_btn)
        loading = LoadingDialog(this)
        sessionManager = SessionManager(this)
        userNameEditText.setText("112") //23
        passwordEditText.setText("password") //123

        loginBtn.setOnClickListener{
            val intSelectButton: Int = userTypeRadioGroup!!.checkedRadioButtonId
            val selectedRadioBtn:RadioButton = findViewById(intSelectButton)
            selectedUserType=selectedRadioBtn.text.toString()
            val userId:Int = userNameEditText.text.toString().toInt()
            val password:String = passwordEditText.text.toString()
            val userDataObj:UserModel = UserModel(selectedUserType.toLowerCase(),userId,password)
            loading.startLoading()
            postDataToApi(userDataObj)
        }
    }

    private fun postDataToApi(userDataObj: UserModel) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://courierpad.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.login(userDataObj)

        retrofitData.enqueue(object : Callback<JsonObject> {

            override fun onResponse(
                call: Call<JsonObject>?,
                response: Response<JsonObject>?

            ) {
                loading.Dismiss()
                val res = response!!.body()
                //sessionManager.saveAuthToken(token)

                val status = res!!.get("status").toString()
                if(status  == "404")
                    Toast.makeText(applicationContext, "User id not found", Toast.LENGTH_SHORT).show()
                else if(status  == "401")
                    Toast.makeText(applicationContext, "Invalid credentials", Toast.LENGTH_SHORT).show()
                else{

                    val token = res!!.get("data").asJsonObject["token"].toString()

                    Toast.makeText(applicationContext, "" + res, Toast.LENGTH_SHORT).show()

                    if(selectedUserType == "Admin")
                        startActivity(Intent(applicationContext,Admin_Home::class.java))
                    else
                        startActivity(Intent(applicationContext,MyOrders::class.java))


                }

            }
            override fun onFailure(call: Call<JsonObject>?, t: Throwable) {
                Toast.makeText(applicationContext," Error "+t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}