package com.sehakhanah.patientapp.modules.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.ApiClient
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.data.remote.objects.*
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.home.MainActivity
import com.sehakhanah.patientapp.utiles.Q
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.login_progrss_lay
import kotlinx.android.synthetic.main.activity_login.username
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verivication)
        phone="+"+intent.getStringExtra("phone")!!
        type=intent.getStringExtra("type")!!
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
                .userVerification(phone, type)
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
                            val intent =
                                Intent(this@VerivicationActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            //username.text.clear()
                            //login_password.text.clear()
                            onObservefaled()
                            Toast.makeText(
                                this@VerivicationActivity,
                                "من فضلك أدخل رقم صحيح",
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
            sendNumber()
        }
    }
}