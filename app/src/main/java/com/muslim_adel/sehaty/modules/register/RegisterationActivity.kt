package com.sehakhanah.patientapp.modules.register

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.muslim_adel.sehaty.utiles.Q
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.ApiClient
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.data.remote.objects.BaseResponce
import com.sehakhanah.patientapp.data.remote.objects.LoginData
import com.sehakhanah.patientapp.modules.base.BaseActivity
import kotlinx.android.synthetic.main.activity_doctors_list.*
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
    private var gender=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)
        datePicker()
        onRegisterClicked()
        handelRdioStates()
        onprivacyClicked()

    }
    private fun handelRdioStates(){
        gender_male.isChecked=true
        gender=1
        gender_male.setOnClickListener {
            gender_male.isChecked=true
            gender_female.isChecked=false
            gender=1
            if(gender_male.isChecked){
                gender=1
                gender_female.isChecked=false
            }else if(gender_female.isChecked){
                gender=2
                gender_male.isChecked=false
            }
        }
        gender_female.setOnClickListener {
            gender_female.isChecked=true
            gender_male.isChecked=false
            gender=2
            if(gender_male.isChecked){
                gender=1
                gender_female.isChecked=false
            }else if(gender_female.isChecked){
                gender=2
                gender_male.isChecked=false
            }
        }

    }

    private fun onRegisterClicked(){
        register_btn.setOnClickListener {
            if(repassword.text.isNotEmpty()&&password.text.isNotEmpty()&&repassword.text.toString()==password.text.toString()){
                if (username.text.isNotEmpty()&&phon_num.text.isNotEmpty()&&email.text.isNotEmpty()&&password.text.isNotEmpty()&&date_of_birth.text.isNotEmpty()&& gender!=-1){
                    onObserveStart()
                    apiClient = ApiClient()
                    sessionManager = SessionManager(this)
                    apiClient.getApiService(this).userregister(
                        username.text.toString(),
                        email.text.toString(),
                        password.text.toString(),
                        "+964${phon_num.text}",
                        date_of_birth.text.toString(),
                        gender.toString()
                    )
                        .enqueue(object : Callback<BaseResponce<LoginData>> {
                            override fun onFailure(
                                call: Call<BaseResponce<LoginData>>,
                                t: Throwable
                            ) {
                                alertNetwork(true)
                            }

                            override fun onResponse(
                                call: Call<BaseResponce<LoginData>>,
                                response: Response<BaseResponce<LoginData>>
                            ) {
                                val registerResponse = response.body()
                                if (registerResponse!!.success) {
                                    val s = response.body()!!.data.toString()
                                    //Toast.makeText(this@RegisterationActivity, s, Toast.LENGTH_SHORT).show()
                                    if (registerResponse?.data!!.status == 200 && registerResponse.data!!.user != null) {
                                        sessionManager.saveAuthToken(registerResponse.data!!.token)
                                        preferences!!.putBoolean(Q.IS_FIRST_TIME, false)
                                        preferences!!.putBoolean(Q.IS_LOGIN, true)
                                        preferences!!.putInteger(
                                            Q.USER_ID,
                                            registerResponse.data!!.user.id.toInt()
                                        )
                                        preferences!!.putString(
                                            Q.USER_NAME,
                                            registerResponse.data!!.user.name
                                        )
                                        preferences!!.putString(
                                            Q.USER_EMAIL,
                                            registerResponse.data!!.user.email
                                        )
                                        preferences!!.putString(
                                            Q.USER_PHONE,
                                            registerResponse.data!!.user.phonenumber.toString()
                                        )
                                        preferences!!.putInteger(
                                            Q.USER_GENDER,
                                            registerResponse.data!!.user.gender_id
                                        )
                                        preferences!!.putString(
                                            Q.USER_BIRTH,
                                            registerResponse.data!!.user.birthday
                                        )
                                        preferences!!.commit()
                                        onObserveSuccess()
                                        val intent =
                                            Intent(
                                                this@RegisterationActivity,
                                                VerivicationActivity::class.java
                                            )
                                        intent.putExtra(
                                            "phone",
                                            registerResponse.data!!.user.phonenumber.toString()
                                        )

                                        intent.putExtra("type", "client")
                                        intent.putExtra("email",email.text.toString())
                                        intent.putExtra("password",password.text.toString())
                                        intent.putExtra("phone","+964${phon_num.text}")

                                        startActivity(intent)
                                        finish()
                                    } else {
                                        onObservefaled()
                                        Toast.makeText(
                                            this@RegisterationActivity,
                                            "البريد الالكتروني مستخدم من قبل",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    //username.text.clear()
                                    //login_password.text.clear()
                                    onObservefaled()
                                    Toast.makeText(
                                        this@RegisterationActivity,
                                        if(registerResponse!!.message!=null)registerResponse!!.message.toString() else "",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }


                        })
                }else{
                    Toast.makeText(
                        this@RegisterationActivity,
                        "من فضلك أكمل البيانات ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else{
                Toast.makeText(
                    this@RegisterationActivity,
                    "كلمة المرور غير متطابقة ",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
    fun datePicker(){
        val textView: TextView  = findViewById(R.id.date_of_birth)
        //textView.text = SimpleDateFormat("yyyy-dd-MM").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy-MM-dd" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            textView.setText(sdf.format(cal.time))

        }

        date_lay.setOnClickListener {
            DatePickerDialog(
                this@RegisterationActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
    private fun onObserveStart(){
        register_progrss_lay.visibility= View.VISIBLE
        register_lay.visibility= View.GONE
    }
    private fun onObserveSuccess(){
        register_progrss_lay.visibility= View.GONE
        register_lay.visibility= View.VISIBLE
    }
    private fun onObservefaled(){
        register_progrss_lay.visibility= View.GONE
        register_lay.visibility= View.VISIBLE
    }
    private fun onprivacyClicked(){
        privacy_btn.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sehakhanah.com/privacyPolicy"))
            startActivity(browserIntent)
        }

    }
}