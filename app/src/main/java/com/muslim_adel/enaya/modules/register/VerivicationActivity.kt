package com.muslim_adel.enaya.modules.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.muslim_adel.enaya.data.remote.objects.SocialLoginRespose
import com.muslim_adel.enaya.modules.register.ForgetPasswordActivity
import com.muslim_adel.enaya.modules.register.LoginActivity
import com.muslim_adel.enaya.utiles.Q
import com.muslim_adel.enaya.R
import com.muslim_adel.enaya.data.remote.apiServices.ApiClient
import com.muslim_adel.enaya.data.remote.apiServices.SessionManager
import com.muslim_adel.enaya.data.remote.objects.*
import com.muslim_adel.enaya.modules.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registeration.*
import kotlinx.android.synthetic.main.activity_verivication.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerivicationActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private var phone =""
    private var type =""
    private var email =""
    private var password =""
    private var key =0
    var selectedType="client"
    var token=""
    var code=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verivication)

        key=intent.getIntExtra("key",0)
        if(key!=1){
            type=intent.getStringExtra("type")!!
            email=intent.getStringExtra("email")!!
            password=intent.getStringExtra("password")!!
            phone=intent.getStringExtra("phone")!!

        }else{
            phone=intent.getStringExtra("phone")!!
            phone=intent.getStringExtra("phone")!!
            code=intent.getStringExtra("code")!!
        }

        onResendClicked()
        onSendClicked()
    }
    private fun onObserveStart() {
        ver_progrss_lay.visibility = View.VISIBLE
    }

    private fun onObserveSuccess() {
        ver_progrss_lay.visibility = View.GONE
    }

    private fun onObservefaled() {
        ver_progrss_lay.visibility = View.GONE
    }

    private fun onResendClicked(){
        retry_btn.setOnClickListener {
            onObserveStart()
            apiClient = ApiClient()
            sessionManager = SessionManager(this)
            apiClient.getApiService(this)
                .userVerification(phone, "client")
                .enqueue(object : Callback<BaseResponce<Verification>> {
                    override fun onFailure(call: Call<BaseResponce<Verification>>, t: Throwable) {
                        alertNetwork(true)
                    }

                    override fun onResponse(
                        call: Call<BaseResponce<Verification>>,
                        response: Response<BaseResponce<Verification>>
                    ) {
                        val loginResponse = response.body()
                        if (loginResponse!!.success) {
                            onObserveSuccess()
                            Toast.makeText(
                                this@VerivicationActivity,
                                "تم ارسال الكود",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            onObservefaled()
                            Toast.makeText(
                                this@VerivicationActivity,
                                "خطا حاول مرة أخري",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }


                })
        }
    }
    private  fun sendNumber(){
        if (message_tf.text.isNotEmpty()){
            onObserveStart()
            apiClient = ApiClient()
            sessionManager = SessionManager(this)
            apiClient.getApiService(this).sendVerificationNum(phone, type,message_tf.text.toString())
                .enqueue(object : Callback<BaseResponce<Verification>> {
                    override fun onFailure(call: Call<BaseResponce<Verification>>, t: Throwable) {
                        alertNetwork(true)
                    }

                    override fun onResponse(
                        call: Call<BaseResponce<Verification>>,
                        response: Response<BaseResponce<Verification>>
                    ) {
                        val registerResponse = response.body()
                        if (response.code()!=500&&registerResponse!!.success) {
                            onObserveSuccess()
                            //login()
                            val intent =
                                Intent(this@VerivicationActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {

                            //username.text.clear()
                            //login_password.text.clear()
                            onObservefaled()
                            Toast.makeText(
                                this@VerivicationActivity,
                                "${registerResponse!!.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }


                })
        }else{
            Toast.makeText(
                this@VerivicationActivity,
                "من فضلك أدخل رقم صحيح ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun onSendClicked(){
        send_message_btn.setOnClickListener {

            if(key!=null&&key==1){
                verifyCode()
            }else{
                sendNumber()
            }
        }
    }
    private fun verifyCode() {

        if (message_tf.text.isNotEmpty() ) {
            onObserveStart()
            apiClient = ApiClient()
            sessionManager = SessionManager(this)
            apiClient.getApiService(this).verifyCode(phone, selectedType,code)
                .enqueue(object : Callback<LoginResponce> {
                    override fun onFailure(call: Call<LoginResponce>, t: Throwable) {
                        alertNetwork(true)
                    }
                    override fun onResponse(
                        call: Call<LoginResponce>,
                        response: Response<LoginResponce>
                    ) {

                        val registerResponse = response.body()
                        if (registerResponse!!.success) {
                            if (!registerResponse!!.message.toString().contains("success -> not verified")&&registerResponse.data!!.token!=null){}
                            token=registerResponse.data!!.token.toString()
                            verifyToken()
                        } else {
                            onObservefaled()
                            Toast.makeText(
                                this@VerivicationActivity,
                                "فشل العملية حاول مرة أخرى",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
        } else {
            Toast.makeText(
                this@VerivicationActivity,
                "من فضلك أكمل البيانات ",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
    private fun verifyToken() {
        var url= Q.VERIFY_TOKEN+token
        onObserveStart()
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).verifyToken(url)
            .enqueue(object : Callback<BaseResponce<SocialLoginRespose>> {
                override fun onFailure(call: Call<BaseResponce<SocialLoginRespose>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<SocialLoginRespose>>,
                    response: Response<BaseResponce<SocialLoginRespose>>
                ) {
                    val registerResponse = response.body()
                    if (registerResponse!!.success) {
                        sessionManager.saveAuthToken(token,0)
                        val intent =
                            Intent(this@VerivicationActivity, ForgetPasswordActivity::class.java)
                        intent.putExtra("key",1)
                        intent.putExtra("phone",phone)
                        intent.putExtra("token",token)
                        startActivity(intent)
                            //resetPassword()
                    } else {
                        onObservefaled()
                        Toast.makeText(
                            this@VerivicationActivity,
                            "فشل العملية حاول مرة أخرى",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }


            })


    }
    
}