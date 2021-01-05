package com.sehakhanah.patientapp.modules.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Scope
import com.google.gson.JsonSyntaxException
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClientnew
import com.muslim_adel.sehaty.data.remote.objects.GenerateToken
import com.muslim_adel.sehaty.data.remote.objects.SocialLoginRespose
import com.muslim_adel.sehaty.modules.register.VerificationPhonActivity
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.ApiClient
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.data.remote.objects.BaseResponce
import com.sehakhanah.patientapp.data.remote.objects.LoginResponce
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.home.MainActivity
import com.sehakhanah.patientapp.utiles.Q
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {
    var fbCallBackManager:CallbackManager?=null
    var gso :GoogleSignInOptions?=null
    var googleSignInClient: GoogleSignInClient?=null
    var account: GoogleSignIn?=null
    private val RC_SIGN_IN = 9001
    private var isLogin = false
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var apiClient2: ApiClientnew
    var socialToken=""
    var socialProvider=""
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initGoogleLogin()
        initFaceBookLogin()
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
    private fun initFaceBookLogin(){
        fbCallBackManager=CallbackManager.Factory.create()
        fb_login_button.registerCallback(fbCallBackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                handleFacebookAccessToken(result!!.accessToken)
               /* Toast.makeText(
                    this@LoginActivity,
                    "${result?.accessToken?.userId}",
                    Toast.LENGTH_SHORT
                ).show()
                socialToken = result?.accessToken?.token.toString()
                socialProvider = "facebook"
                generatTokenObserver()*/

            }

            override fun onCancel() {
                Toast.makeText(this@LoginActivity, "fb login canceled", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException?) {
                Toast.makeText(this@LoginActivity, "fb login error", Toast.LENGTH_SHORT).show()
            }

        })
    }
    private fun handleFacebookAccessToken(token: AccessToken) {

        try {
            val request = GraphRequest.newMeRequest(token) { jObject: JSONObject?, _: GraphResponse? ->
                jObject?.let {
                    var id = if (jObject.isNull("id")) "" else jObject.getString("id")
                    var name = if (jObject.isNull("name")) "" else jObject.getString("name")
                    socialToken = if (jObject.isNull("access_token")) "" else jObject.getString("email")
                    socialProvider = "facebook"
                    gotToSocialVerification()
                    //generatTokenObserver()

                    //val gender = jObject.getString("gender")
                    //val birthday = jObject.getString("birthday") // 01/31/1980 format
                }

            }

            val parameters = Bundle()
            parameters.putString("fields", "id,name,first_name,last_name,email")
            request.parameters = parameters
            request.executeAsync()

        } catch (e: JSONException) {

        } catch (e: JsonSyntaxException) {

        } catch (e: Exception) {
        }
    }
    private fun initGoogleLogin(){
        val serverClientId = getString(R.string.server_client_id)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestScopes(Scope(Scopes.DRIVE_APPFOLDER))
            .requestServerAuthCode(serverClientId)
            .requestIdToken(serverClientId)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInButton = findViewById<SignInButton>(R.id.goole_sign_in_button)
        signInButton.setSize(SignInButton.SIZE_STANDARD)
        setGooglePlusButtonText(signInButton, getString(R.string.continue_with_google))
        signInButton.setOnClickListener {
                signin()
        }
    }
    private fun signin(){
        googleSignInClient!!.signOut()
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fbCallBackManager?.onActivityResult(requestCode, resultCode, data)
       /* val intent =
            Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()*/
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {

                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    handleGoogleSignInResult(account)

                } catch (e: ApiException) {
                    e.printStackTrace()

                }

            } else {

            }
        }

    }
    private fun handleGoogleSignInResult(account: GoogleSignInAccount?) {
        gotToSocialVerification()
        //generatTokenObserver()
        //socialToken=account!!.idToken.toString()
       // socialProvider="google"
        //Toast.makeText(this, account!!.id.toString(), Toast.LENGTH_SHORT).show()
        /*mAdwaaViewModel?.isViewBusy = true
        mUserViewModel!!.isGoogleAccount = true
        mUserViewModel!!.fullName = String.format("%s %s", account!!.givenName, account.familyName)
        mUserViewModel!!.email = account.email ?: ""
        mUserViewModel!!.userName = account.displayName ?: ""
        mUserViewModel!!.parentName = account.familyName ?: ""
        mUserViewModel!!.imageName = account.photoUrl.toString()*/


    }
    private fun setGooglePlusButtonText(signInButton: SignInButton, buttonText: String) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (i in 0 until signInButton.childCount) {
            val v = signInButton.getChildAt(i)
            if (v is TextView) {
                v.text = buttonText
                return
            }
        }
    }
    private fun generatTokenObserver(){
            onObserveStart()
            apiClient2 = ApiClientnew()
            sessionManager = SessionManager(this)
            apiClient2.getApiService(this)
                .generateToken(
                    "client_credentials",
                    "7",
                    "OYFfHRim0QjFYHSuBdWc49arCyII99agIFdpKV7e",
                    "*"
                )
                .enqueue(object : Callback<GenerateToken> {
                    override fun onFailure(call: Call<GenerateToken>, t: Throwable) {
                        alertNetwork(true)
                    }

                    override fun onResponse(
                        call: Call<GenerateToken>,
                        response: Response<GenerateToken>
                    ) {
                        val loginResponse = response
                        if (loginResponse!!.isSuccessful && loginResponse.body()!!.access_token != null) {
                            onObserveSuccess()
                            sessionManager.saveAuthToken(loginResponse.body()!!.access_token)
                            // preferences!!.putBoolean(Q.IS_FIRST_TIME,false)
                            // preferences!!.putBoolean(Q.IS_LOGIN,true)
                            // preferences!!.putInteger(Q.USER_ID,registerResponse.data!!.user.id.toInt())
                            // preferences!!.putString(Q.USER_NAME,registerResponse.data!!.user.name)
                            //preferences!!.putString(Q.USER_EMAIL,registerResponse.data!!.user.email)
                            // preferences!!.putString(Q.USER_PHONE,registerResponse.data!!.user.phonenumber.toString())
                            // preferences!!.putInteger(Q.USER_GENDER,registerResponse.data!!.user.gender_id)
                            // preferences!!.putString(Q.USER_BIRTH,registerResponse.data!!.user.birthday)
                            //  preferences!!.commit()
                            onObserveSuccess()
                            socialLoginObserver()
                            /*val intent =
                                Intent(this@LoginActivity, VerivicationActivity::class.java)
                            intent.putExtra("phone","")
                            intent.putExtra("type","client")
                            startActivity(intent)
                            finish()*/
                        } else {
                            onObservefaled()
                            Toast.makeText(
                                this@LoginActivity,
                                "خطا حاول مرة أخري",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }

                })


    }
    private fun socialLoginObserver(){
        onObserveStart()
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this)
            .socialLogin(socialToken, socialProvider)
            .enqueue(object : Callback<BaseResponce<SocialLoginRespose>> {
                override fun onFailure(call: Call<BaseResponce<SocialLoginRespose>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<SocialLoginRespose>>,
                    response: Response<BaseResponce<SocialLoginRespose>>
                ) {
                    val loginResponse = response
                    if (loginResponse!!.isSuccessful && loginResponse.body()!!.data != null) {
                        onObserveSuccess()
                        sessionManager.saveAuthToken(loginResponse.body()!!.data!!.token)
                        preferences!!.putBoolean(Q.IS_FIRST_TIME, false)
                        preferences!!.putBoolean(Q.IS_LOGIN, true)
                        preferences!!.putInteger(
                            Q.USER_ID,
                            loginResponse!!.body()!!.data!!.user.id.toInt()
                        )
                        preferences!!.putString(
                            Q.USER_NAME,
                            loginResponse!!.body()!!.data!!.user.name
                        )
                        preferences!!.putString(
                            Q.USER_EMAIL,
                            loginResponse!!.body()!!.data!!.user.email
                        )
                        preferences!!.putString(
                            Q.USER_PHONE,
                            loginResponse!!.body()!!.data!!.user.phonenumber.toString()
                        )
                        preferences!!.putInteger(
                            Q.USER_GENDER,
                            loginResponse!!.body()!!.data!!.user.gender_id
                        )
                        preferences!!.putString(
                            Q.USER_BIRTH,
                            loginResponse!!.body()!!.data!!.user.birthday
                        )
                        preferences!!.commit()
                        onObserveSuccess()
                        val intent =
                            Intent(this@LoginActivity, VerivicationActivity::class.java)
                        intent.putExtra("phone", "")
                        intent.putExtra("type", "client")
                        startActivity(intent)
                        finish()
                    } else {
                        onObservefaled()
                        Toast.makeText(
                            this@LoginActivity,
                            "خطا حاول مرة أخري",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

            })


    }


private fun gotToSocialVerification(){
    preferences!!.putBoolean(Q.IS_FIRST_TIME, false)
    preferences!!.putBoolean(Q.IS_LOGIN, true)
    preferences!!.commit()
    val intent =
        Intent(this@LoginActivity, VerificationPhonActivity::class.java)
    startActivity(intent)
    finish()
}

}