package com.sehakhanah.patientapp.modules.settings

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.ApiClient
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.data.remote.objects.BaseResponce
import com.sehakhanah.patientapp.data.remote.objects.User
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.home.MainActivity
import com.sehakhanah.patientapp.modules.register.LoginActivity
import com.sehakhanah.patientapp.modules.splash.SplashActivity
import com.sehakhanah.patientapp.utiles.Q
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.date_of_birth
import kotlinx.android.synthetic.main.activity_edit_profile.email
import kotlinx.android.synthetic.main.activity_edit_profile.phon_num
import kotlinx.android.synthetic.main.activity_edit_profile.username
import kotlinx.android.synthetic.main.activity_registeration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private var gender = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        initBottomNavigation()
        setcurrentdata()
        datePicker()
        onSaveClicked()
        handelRdioStates()
    }

    private fun onSaveClicked() {
        edite_save_btn.setOnClickListener {
            if (username.text.isNotEmpty() && phon_num.text.isNotEmpty() && email.text.isNotEmpty() && date_of_birth.text.isNotEmpty() && gender != -1) {
                onObserveStart()
                apiClient = ApiClient()
                sessionManager = SessionManager(this)
                apiClient.getApiService(this).updateProfile(
                    username.text.toString(),
                    email.text.toString(),
                    phon_num.text.toString(),
                    gender.toString(),
                    date_of_birth.text.toString()
                )
                    .enqueue(object : Callback<BaseResponce<User>> {
                        override fun onFailure(call: Call<BaseResponce<User>>, t: Throwable) {
                            alertNetwork(true)
                        }

                        override fun onResponse(
                            call: Call<BaseResponce<User>>,
                            response: Response<BaseResponce<User>>
                        ) {
                            val registerResponse = response.body()
                            if (registerResponse!!.success) {
                                val s = response.body()!!.data.toString()
                                Toast.makeText(
                                    this@EditProfileActivity,
                                    "تم تعديل البيانات بنجاح",
                                    Toast.LENGTH_SHORT
                                ).show()

                                onObserveSuccess()
                                logOut()
                            } else {
                                //username.text.clear()
                                //login_password.text.clear()
                                onObservefaled()
                                Toast.makeText(
                                    this@EditProfileActivity,
                                    "فشل العملية حاول مرة أخرى",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }


                    })
            } else {
                Toast.makeText(
                    this@EditProfileActivity,
                    "من فضلك أكمل البيانات ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setcurrentdata() {
        username.setText(preferences!!.getString(Q.USER_NAME, ""), TextView.BufferType.EDITABLE)
        phon_num.setText(preferences!!.getString(Q.USER_PHONE, ""), TextView.BufferType.EDITABLE)
        email.setText(preferences!!.getString(Q.USER_EMAIL, ""), TextView.BufferType.EDITABLE)
        date_of_birth.setText(
            preferences!!.getString(Q.USER_BIRTH, ""),
            TextView.BufferType.EDITABLE
        )
        if (preferences!!.getInteger(Q.USER_GENDER, -1) == 1) {
            mail_btn.isChecked = true
            femail_btn.isChecked = false
        } else {
            mail_btn.isChecked = false
            femail_btn.isChecked = true
        }

    }

    fun datePicker() {
        val textView: TextView = findViewById(R.id.date_of_birth)
        //textView.text = SimpleDateFormat("yyyy-dd-MM").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "yyyy-dd-MM" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                textView.setText(sdf.format(cal.time))

            }

        edit_date_lay.setOnClickListener {
            DatePickerDialog(
                this@EditProfileActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun onObserveStart() {
        edite_progrss_lay.visibility = View.VISIBLE
        edite_lay.visibility = View.GONE
    }

    private fun onObserveSuccess() {
        edite_progrss_lay.visibility = View.GONE
        edite_lay.visibility = View.VISIBLE
    }

    private fun onObservefaled() {
        edite_progrss_lay.visibility = View.GONE
        edite_lay.visibility = View.GONE
    }

    private fun handelRdioStates() {
        mail_btn.isChecked = true
        gender = 1
        mail_btn.setOnClickListener {
            mail_btn.isChecked = true
            femail_btn.isChecked = false
            gender = 1
            if (mail_btn.isChecked) {
                gender = 1
                femail_btn.isChecked = false
            } else if (femail_btn.isChecked) {
                gender = 2
                mail_btn.isChecked = false
            }
        }
        femail_btn.setOnClickListener {
            femail_btn.isChecked = true
            mail_btn.isChecked = false
            gender = 2
            if (mail_btn.isChecked) {
                gender = 1
                femail_btn.isChecked = false
            } else if (femail_btn.isChecked) {
                gender = 2
                mail_btn.isChecked = false
            }
        }

    }

    private fun logOut() {
        sessionManager.saveAuthToken("")
        preferences!!.putBoolean(Q.IS_FIRST_TIME, true)
        preferences!!.putBoolean(Q.IS_LOGIN, false)
        preferences!!.putInteger(Q.USER_ID, -1)
        preferences!!.putString(Q.USER_NAME, "")
        preferences!!.putString(Q.USER_EMAIL, "")
        preferences!!.putString(Q.USER_PHONE, "")
        preferences!!.putInteger(Q.USER_GENDER, -1)
        preferences!!.commit()
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun initBottomNavigation(){

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    intent= Intent(this,MainActivity::class.java)
                    intent.putExtra("navK",0)
                    startActivity(intent)
                }
                R.id.navigation_offers -> {
                    intent= Intent(this,MainActivity::class.java)
                    intent.putExtra("navK",1)
                    startActivity(intent)
                }
                R.id.navigation_appointment -> {
                    intent= Intent(this,MainActivity::class.java)
                    intent.putExtra("navK",2)
                    startActivity(intent)
                }
                R.id.navigation_extras->{
                    intent= Intent(this,MainActivity::class.java)
                    intent.putExtra("navK",3)
                    startActivity(intent)
                }
            }
            false
        }
        bottomNavigationView19.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView19.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
}