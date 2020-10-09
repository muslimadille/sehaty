package com.muslim_adel.sehaty.modules.register

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.BaseResponce
import com.muslim_adel.sehaty.data.remote.objects.LoginData
import com.muslim_adel.sehaty.data.remote.objects.LoginResponce
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.home.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registeration.*
import kotlinx.android.synthetic.main.activity_registeration.username
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class RegisterationActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)
        datePicker()

        register_btn.setOnClickListener {
            if (username.text.isNotEmpty()&&phon_num.text.isNotEmpty()&&email.text.isNotEmpty()&&password.text.isNotEmpty()&&date_of_birth.text.isNotEmpty()){
                apiClient = ApiClient()
                sessionManager = SessionManager(this)
                apiClient.getApiService(this).userregister(username.text.toString(),email.text.toString(),password.text.toString(),phon_num.text.toString(),date_of_birth.text.toString(),1.toString())
                    .enqueue(object : Callback<BaseResponce<LoginData>> {
                        override fun onFailure(call: Call<BaseResponce<LoginData>>, t: Throwable) {
                            Toast.makeText(
                                this@RegisterationActivity,
                                "فشل في الاتصال",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                        override fun onResponse(
                            call: Call<BaseResponce<LoginData>>,
                            response: Response<BaseResponce<LoginData>>
                        ) {
                            val registerResponse = response.body()
                            if (registerResponse!!.success) {
                                val s = response.body()!!.data.toString()
                                Toast.makeText(this@RegisterationActivity, s, Toast.LENGTH_SHORT).show()
                                if (registerResponse?.data!!.status == 200 && registerResponse.data!!.user!=null) {
                                    sessionManager.saveAuthToken(registerResponse.data!!.token)
                                    val intent =
                                        Intent(this@RegisterationActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@RegisterationActivity,
                                        "البريد الالكتروني مستخدم من قبل",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                //username.text.clear()
                                //login_password.text.clear()
                                Toast.makeText(
                                    this@RegisterationActivity,
                                    "فشل في التسجبل",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }


                    })
            }
        }
    }
    fun datePicker(){
        val textView: EditText  = findViewById(R.id.date_of_birth)
        textView.setText(SimpleDateFormat("yyyy-dd-MM").format(System.currentTimeMillis()))

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy-dd-MM" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            textView.setText(sdf.format(cal.time))

        }

        textView.setOnClickListener {
            DatePickerDialog(this@RegisterationActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }
}