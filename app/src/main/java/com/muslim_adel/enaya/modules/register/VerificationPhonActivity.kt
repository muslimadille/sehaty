package com.muslim_adel.enaya.modules.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.muslim_adel.enaya.utiles.Q
import com.muslim_adel.enaya.R
import com.muslim_adel.enaya.data.remote.apiServices.ApiClient
import com.muslim_adel.enaya.data.remote.apiServices.SessionManager
import com.muslim_adel.enaya.data.remote.objects.BaseResponce
import com.muslim_adel.enaya.data.remote.objects.Verification
import com.muslim_adel.enaya.modules.base.BaseActivity
import com.muslim_adel.enaya.modules.register.VerivicationActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_verification_phon.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerificationPhonActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private var phone =""
    private var type =""
    private var email =""
    private var password =""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_phon)
        //phone=intent.getStringExtra("phone")!!
        email=intent.getStringExtra("email")!!
        password=intent.getStringExtra("password")!!
        type="client"
        onResendClicked()
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
        send_message_btn.setOnClickListener {
            phone= Q.PHONE_KEY+message_tf.text.toString()
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
                        if (loginResponse!=null&&loginResponse!!.success) {
                            onObserveSuccess()
                            val intent =
                                Intent(this@VerificationPhonActivity, VerivicationActivity::class.java)
                            intent.putExtra("phone",Q.PHONE_KEY+message_tf.text.toString())
                            intent.putExtra("type","client")
                            intent.putExtra("email",email)
                            intent.putExtra("password",password)
                            startActivity(intent)
                            finish()

                        } else {
                            onObservefaled()
                            Toast.makeText(
                                this@VerificationPhonActivity,
                                "خطا حاول مرة أخري",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }


                })
        }
    }
}