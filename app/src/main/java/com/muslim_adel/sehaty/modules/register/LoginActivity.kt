package com.muslim_adel.sehaty.modules.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.LoginResponce
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.home.MainActivity
import com.muslim_adel.sehaty.utiles.Q
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.username
import kotlinx.android.synthetic.main.activity_registeration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {
    private var isLogin = false
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        registration_btn.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterationActivity::class.java)
            startActivity(intent)

        }
        Login_btn.setOnClickListener {

            if (username.text.isNotEmpty() && login_password.text.isNotEmpty()) {
                onObserveStart()
                apiClient = ApiClient()
                sessionManager = SessionManager(this)
                apiClient.getApiService(this)
                    .login(username.text.toString(), login_password.text.toString())
                    .enqueue(object : Callback<LoginResponce> {
                        override fun onFailure(call: Call<LoginResponce>, t: Throwable) {
                            alertNetwork(true)
                        }

                        override fun onResponse(
                            call: Call<LoginResponce>,
                            response: Response<LoginResponce>
                        ) {
                            val loginResponse = response.body()
                            if (loginResponse!!.success) {
                                if (loginResponse?.data!!.status == 200 && loginResponse.data.user != null) {
                                    username.text.clear()
                                    login_password.text.clear()
                                    sessionManager.saveAuthToken(loginResponse.data.token)
                                    preferences!!.putBoolean(Q.IS_FIRST_TIME, false)
                                    preferences!!.putBoolean(Q.IS_LOGIN, true)
                                    preferences!!.putInteger(
                                        Q.USER_ID,
                                        loginResponse.data.user.id.toInt()
                                    )

                                    preferences!!.putString(
                                        Q.USER_NAME,
                                        loginResponse.data.user.name
                                    )
                                    preferences!!.putString(
                                        Q.USER_EMAIL,
                                        loginResponse.data.user.email
                                    )
                                    preferences!!.putString(
                                        Q.USER_PHONE,
                                        loginResponse.data.user.phonenumber.toString()
                                    )
                                    preferences!!.putInteger(
                                        Q.USER_GENDER,
                                        loginResponse.data.user.gender_id
                                    )
                                    preferences!!.putString(
                                        Q.USER_BIRTH,
                                        loginResponse.data.user.birthday
                                    )

                                    preferences!!.commit()
                                    onObserveSuccess()
                                    val intent =
                                        Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            } else {
                                onObservefaled()
                                username.text.clear()
                                login_password.text.clear()
                                Toast.makeText(
                                    this@LoginActivity,
                                    "كلمة المرور او البريد الالكتروني غير صحيح ",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }


                    })
            }

        }
    }

    private fun onObserveStart() {
        login_progrss_lay.visibility = View.VISIBLE
    }

    private fun onObserveSuccess() {
        login_progrss_lay.visibility = View.GONE
    }

    private fun onObservefaled() {
        login_progrss_lay.visibility = View.GONE
    }

}