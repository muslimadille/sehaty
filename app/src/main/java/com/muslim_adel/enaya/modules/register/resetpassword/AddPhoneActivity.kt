package com.muslim_adel.enaya.modules.register.resetpassword

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.muslim_adel.enaya.data.remote.objects.CreateCodeModel
import com.muslim_adel.enaya.utiles.Q
import com.muslim_adel.enaya.R
import com.muslim_adel.enaya.data.remote.apiServices.ApiClient
import com.muslim_adel.enaya.data.remote.apiServices.SessionManager
import com.muslim_adel.enaya.data.remote.objects.BaseResponce
import com.muslim_adel.enaya.modules.base.BaseActivity
import com.muslim_adel.enaya.modules.register.VerivicationActivity
import kotlinx.android.synthetic.main.activity_add_phone.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPhoneActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient


    var selectedType="client"
    var phone=""
    var code=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_phone)
        rsp_btn.setOnClickListener {
            getCode()
        }

    }
    private fun getCode() {

        if (message_tf.text.isNotEmpty() &&message_tf.text.toString().length==10 ) {
            phone= Q.PHONE_KEY+message_tf.text
            apiClient = ApiClient()
            sessionManager = SessionManager(this)
            apiClient.getApiService(this).createCode(phone, selectedType)
                .enqueue(object : Callback<BaseResponce<CreateCodeModel>> {
                    override fun onFailure(call: Call<BaseResponce<CreateCodeModel>>, t: Throwable) {
                        alertNetwork(true)
                    }

                    override fun onResponse(
                        call: Call<BaseResponce<CreateCodeModel>>,
                        response: Response<BaseResponce<CreateCodeModel>>
                    ) {
                        val registerResponse = response.body()
                        if (registerResponse!!.success) {
                            val intent =
                                Intent(this@AddPhoneActivity, VerivicationActivity::class.java)
                            intent.putExtra("key",1)
                            intent.putExtra("phone",phone)
                            intent.putExtra("code",registerResponse!!.data!!.user.code.toString())
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@AddPhoneActivity,
                                "فشل العملية حاول مرة أخرى",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }


                })
        } else {
            Toast.makeText(
                this@AddPhoneActivity,
                "من فضلك أكمل البيانات ",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

}