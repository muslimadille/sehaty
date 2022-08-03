package com.muslim_adel.enaya.modules.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.muslim_adel.enaya.data.remote.objects.CreateCodeModel
import com.muslim_adel.enaya.data.remote.objects.SocialLoginRespose
import com.muslim_adel.enaya.modules.base.SpinnerAdapterCustomFont
import com.muslim_adel.enaya.utiles.Q
import com.muslim_adel.enaya.R
import com.muslim_adel.enaya.data.remote.apiServices.ApiClient
import com.muslim_adel.enaya.data.remote.apiServices.SessionManager
import com.muslim_adel.enaya.data.remote.objects.BaseResponce
import com.muslim_adel.enaya.data.remote.objects.LoginResponce
import com.muslim_adel.enaya.modules.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_forget_password.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgetPasswordActivity : BaseActivity() {
    private lateinit var typsSpinnerAdapter: SpinnerAdapterCustomFont
    private var typesList = ArrayList<String>()
    var selectedType="client"
    var email=""
    var phone=""
    var pasword=""
    var conpasword=""
    var code=""
    var token=""

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        onSaveClicked()
    }

    private fun validateInputs(){
        /*if(for_phon_num.text.isNotEmpty()){
            phone=for_phon_num.text.toString()
        }else{
            Toast.makeText(this, "ادخل  رقم الموبايل", Toast.LENGTH_SHORT).show()
        }*/

        phone=intent.getStringExtra("phone")!!
        token=intent.getStringExtra("token")!!
        if(old_password.text.isNotEmpty()){
            pasword=old_password.text.toString()
        }else{
            Toast.makeText(this, "أدخل رمز الدخول الجديد", Toast.LENGTH_SHORT).show()
        }
        if(new_password.text.isNotEmpty()){
            conpasword=new_password.text.toString()
        }else{
            Toast.makeText(this, "أعد إدخال  رمز الدخول الجديد", Toast.LENGTH_SHORT).show()
        }


    }
    private fun onSaveClicked(){
        save_bt.setOnClickListener {
            validateInputs()
            //getCode()
           resetPassword()
        }
    }
    private fun getCode() {
        
            if (phone.isNotEmpty()  && pasword.isNotEmpty() && conpasword.isNotEmpty() ) {
                onObserveStart()
                apiClient = ApiClient()
                sessionManager = SessionManager(this)
                apiClient.getApiService(this).createCode(Q.PHONE_KEY+phone, selectedType)
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
                                phone=registerResponse.data!!.user.phonenumber
                                email=registerResponse.data!!.user.email
                                code=registerResponse.data!!.user.code.toString()
                                verifyCode()
                            } else {
                                onObservefaled()
                                Toast.makeText(
                                    this@ForgetPasswordActivity,
                                    "فشل العملية حاول مرة أخرى",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }


                    })
            } else {
                Toast.makeText(
                    this@ForgetPasswordActivity,
                    "من فضلك أكمل البيانات ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        
    }
    private fun verifyCode() {

        if (email.isNotEmpty()  && pasword.isNotEmpty() && conpasword.isNotEmpty() ) {
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
                            token=registerResponse.data!!.token.toString()
                            verifyToken()
                        } else {
                            onObservefaled()
                            Toast.makeText(
                                this@ForgetPasswordActivity,
                                "فشل العملية حاول مرة أخرى",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }


                })
        } else {
            Toast.makeText(
                this@ForgetPasswordActivity,
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
                            sessionManager.saveAuthToken(token,registerResponse!!.data!!.user!!.country_id!!)
                            resetPassword()
                        } else {
                            onObservefaled()
                            Toast.makeText(
                                this@ForgetPasswordActivity,
                                "فشل العملية حاول مرة أخرى",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }


                })


    }
    private fun resetPassword() {
        onObserveStart()
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).passwordReset(phone,selectedType,token,pasword,conpasword)
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
                        onObserveSuccess()
                        Toast.makeText(
                            this@ForgetPasswordActivity,
                            "تم تعديل رمز الدخول بنجاح",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent =
                            Intent(this@ForgetPasswordActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        onObservefaled()
                        Toast.makeText(
                            this@ForgetPasswordActivity,
                            "فشل العملية حاول مرة أخرى",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }


            })


    }


    private fun onObserveStart() {
        forgetpw_progrss_lay.visibility = View.VISIBLE
        mainLay.visibility = View.GONE
    }

    private fun onObserveSuccess() {
        forgetpw_progrss_lay.visibility = View.GONE
        mainLay.visibility = View.VISIBLE
    }

    private fun onObservefaled() {
        forgetpw_progrss_lay.visibility = View.GONE
        mainLay.visibility = View.GONE
    }
}