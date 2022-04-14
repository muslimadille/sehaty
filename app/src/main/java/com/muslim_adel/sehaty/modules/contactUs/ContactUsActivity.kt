package com.sehakhanah.patientapp.modules.contactUs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.muslim_adel.sehaty.modules.settings.ContactUsModel
import com.muslim_adel.sehaty.utiles.Q
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.ApiClient
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.data.remote.objects.BaseResponce
import com.sehakhanah.patientapp.data.remote.objects.LoginResponce
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.home.MainActivity
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_about_us.bottomNavigationView
import kotlinx.android.synthetic.main.activity_contact_us.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_verivication.*
import kotlinx.android.synthetic.main.activity_verivication.message_tf
import kotlinx.android.synthetic.main.activity_verivication.send_message_btn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactUsActivity : BaseActivity() {
    private var name=""
    private var email=""
    private var phone=""
    private var comment=""
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        initBottomNavigation()
        onSendClicked()
        getContactUsData()
    }
    private fun initBottomNavigation(){

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    intent= Intent(this, MainActivity::class.java)
                    intent.putExtra("navK",0)
                    startActivity(intent)
                }
                R.id.navigation_offers -> {
                    intent= Intent(this, MainActivity::class.java)
                    intent.putExtra("navK",1)
                    startActivity(intent)
                }
                R.id.navigation_appointment -> {
                    intent= Intent(this, MainActivity::class.java)
                    intent.putExtra("navK",2)
                    startActivity(intent)
                }
                R.id.navigation_extras->{
                    intent= Intent(this, MainActivity::class.java)
                    intent.putExtra("navK",3)
                    startActivity(intent)
                }
            }
            false
        }
        bottomNavigationView.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
    private fun messageSend() {

        if (message_tf.text.isNotEmpty() ) {
            onObserveStart()
            apiClient = ApiClient()
            sessionManager = SessionManager(this)
            apiClient.getApiService(this).sendContactUs(name, phone,email,comment)
                .enqueue(object : Callback<BaseResponce<Any>> {
                    override fun onFailure(call: Call<BaseResponce<Any>>, t: Throwable) {
                        alertNetwork(true)
                    }

                    override fun onResponse(
                        call: Call<BaseResponce<Any>>,
                        response: Response<BaseResponce<Any>>
                    ) {

                        val registerResponse = response.body()
                        if (registerResponse!!.success) {
                            onObserveSuccess()
                            Toast.makeText(this@ContactUsActivity, "تم ارسال رسالتك بنجاح", Toast.LENGTH_SHORT).show()
                        } else {
                            onObservefaled()
                            Toast.makeText(
                                this@ContactUsActivity,
                                "فشل العملية حاول مرة أخرى",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
        } else {
            Toast.makeText(
                this@ContactUsActivity,
                "من فضلك أكمل البيانات ",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
    fun getContactUsData(){
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this)
            .contactUsData()
            .enqueue(object : Callback<BaseResponce<List<ContactUsModel>>> {
                override fun onFailure(call: Call<BaseResponce<List<ContactUsModel>>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<List<ContactUsModel>>>,
                    response: Response<BaseResponce<List<ContactUsModel>>>
                ) {
                    val myResponse = response.body()
                    if (myResponse!!.success) {
                        phone_num_btn.setText(myResponse.data!![0].phonenum.toString())
                    } else {

                        Toast.makeText(
                            this@ContactUsActivity,
                            "Error:${myResponse.data}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }


            })
    }

    private fun onSendClicked(){
        send_message_btn.setOnClickListener { 
            if(message_tf.text.toString().isNotEmpty()){
                validateData()
            }else{
                Toast.makeText(this, "قم بإدخال رسالة", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private  fun validateData(){
        name= preferences!!.getString(Q.USER_NAME,"user")
        email= preferences!!.getString(Q.USER_EMAIL,"user@email.com")
        phone= preferences!!.getString(Q.USER_PHONE, "07703454657").replace(Q.PHONE_KEY,"0")
        comment=message_tf.text.toString()
        messageSend()
    }
    private fun onObserveStart() {
        progrss_lay.visibility = View.VISIBLE
    }

    private fun onObserveSuccess() {
        progrss_lay.visibility = View.GONE
    }

    private fun onObservefaled() {
        progrss_lay.visibility = View.GONE
    }

}