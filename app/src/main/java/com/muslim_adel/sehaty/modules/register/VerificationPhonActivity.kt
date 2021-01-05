package com.muslim_adel.sehaty.modules.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.ApiClient
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.data.remote.objects.BaseResponce
import com.sehakhanah.patientapp.data.remote.objects.Verification
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.register.VerivicationActivity
import kotlinx.android.synthetic.main.activity_verification_phon.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerificationPhonActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private var phone =""
    private var type =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_phon)
        //phone=intent.getStringExtra("phone")!!
        type="clint"
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
            phone="+964"+message_tf.text.toString()
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
                            intent.putExtra("phone","+964"+message_tf.text.toString())
                            intent.putExtra("type","clint")
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