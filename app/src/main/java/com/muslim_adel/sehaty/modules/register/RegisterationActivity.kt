package com.muslim_adel.sehaty.modules.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.LoginResponce
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.home.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registeration.*
import kotlinx.android.synthetic.main.activity_registeration.username
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterationActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)
        register_btn.setOnClickListener {
            if (username.text.isNotEmpty()&&phon_num.text.isNotEmpty()&&email.text.isNotEmpty()&&password.text.isNotEmpty()&&date_of_birth.text.isNotEmpty()){
                apiClient = ApiClient()
                sessionManager = SessionManager(this)
                apiClient.getApiService(this).userregister(username.text.toString(),email.text.toString(),password.text.toString(),phon_num.text.toString(),date_of_birth.text.toString(),"maile")
                    .enqueue(object : Callback<LoginResponce> {
                        override fun onFailure(call: Call<LoginResponce>, t: Throwable) {
                            Toast.makeText(this@RegisterationActivity, "فشل في الاتصال", Toast.LENGTH_SHORT).show()

                        }

                        override fun onResponse(call: Call<LoginResponce>, response: Response<LoginResponce>) {
                            val registerResponse = response.body()
                            if(registerResponse!!.success){
                                if (registerResponse?.data!!.status  == 200 && registerResponse.data.user!= null) {
                                    sessionManager.saveAuthToken(registerResponse.data.token)
                                    val intent = Intent(this@RegisterationActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this@RegisterationActivity, "البريد الالكتروني مستخدم من قبل", Toast.LENGTH_SHORT).show()
                                }
                            }else{
                                //username.text.clear()
                                //login_password.text.clear()
                                Toast.makeText(this@RegisterationActivity, "فشل في التسجبل", Toast.LENGTH_SHORT).show()
                            }

                        }


                    })
            }
        }
    }
}