package com.muslim_adel.enaya.data.remote.apiServices

import android.content.Context
import com.muslim_adel.enaya.utiles.Q
import com.muslim_adel.enaya.utiles.ComplexPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context) : Interceptor {
    private val sessionManager = SessionManager(context)
    var preferences: ComplexPreferences? = null
    var mcontext=context

    override fun intercept(chain: Interceptor.Chain): Response {
        preferences = ComplexPreferences.getComplexPreferences(mcontext!!, Q.PREF_FILE, Q.MODE_PRIVATE)

        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        if(sessionManager.fetchAuthToken()==null){
            var token=preferences!!.getString("tok","")
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }else{
            sessionManager.fetchAuthToken()?.let {
                requestBuilder.addHeader("Authorization", "Bearer $it")
            }
        }
        //handle country id
        var countryId=preferences!!.getInteger("COUNTRY_ID",1)
        requestBuilder.addHeader("Country-id", countryId.toString())

        return chain.proceed(requestBuilder.build())
    }
}