package com.sehakhanah.patientapp.modules.home.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.modules.contactUs.AboutUsActivity
import com.sehakhanah.patientapp.modules.contactUs.ContactUsActivity
import com.sehakhanah.patientapp.modules.favorits.FavoritsActivity
import com.sehakhanah.patientapp.modules.home.MainActivity
import com.sehakhanah.patientapp.modules.introSlider.IntroWizardActivity
import com.sehakhanah.patientapp.modules.register.LoginActivity
import com.sehakhanah.patientapp.modules.settings.SettingsActivity
import com.sehakhanah.patientapp.modules.splash.SplashActivity
import com.sehakhanah.patientapp.utiles.Q
import kotlinx.android.synthetic.main.extras_fragment.*
import kotlinx.android.synthetic.main.settings_activity.*


class ExstarsFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.extras_fragment, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sessionManager = SessionManager(mContext!!)

        onLogoutClicked()
        onSettingsClicked()
        onFavoritesClicked()
        onContactUsClicked()
        onAboutUsClicked()
        onHowAppWorkClicked()
        initLay()
    }

    private fun initLay(){
        if(!mContext!!.preferences!!.getBoolean(Q.IS_LOGIN,false)){
            logout_txt.text=mContext!!.getString(R.string.login)
            fav_lay.visibility=View.GONE
        }else{
            fav_lay.visibility=View.VISIBLE
            logout_txt.text=mContext!!.getString(R.string.log_out)
        }

    }
    private fun onLogoutClicked(){
        logout_btn.setOnClickListener {
            sessionManager.saveAuthToken("")
            mContext!!.preferences!!.putBoolean(Q.IS_FIRST_TIME,true)
            mContext!!.preferences!!.putBoolean(Q.IS_LOGIN,false)
            mContext!!.preferences!!.putInteger(Q.USER_ID,-1)
            mContext!!.preferences!!.putString(Q.USER_NAME,"")
            mContext!!.preferences!!.putString(Q.USER_EMAIL,"")
            mContext!!.preferences!!.putString(Q.USER_PHONE,"")
            mContext!!.preferences!!.putInteger(Q.USER_GENDER,-1)
            mContext!!.preferences!!.commit()
            val intent = Intent(mContext, SplashActivity::class.java)
            startActivity(intent)
            mContext!!.finish()
        }
    }
    private fun onSettingsClicked(){
        setting_btn.setOnClickListener {
            val intent = Intent(context, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
    private fun onFavoritesClicked(){
        favorites_btn.setOnClickListener {
            val intent = Intent(context, FavoritsActivity::class.java)
            startActivity(intent)
        }
    }
    private fun onContactUsClicked(){
        contact_us_btn.setOnClickListener {
            val intent = Intent(context, ContactUsActivity::class.java)
            startActivity(intent)
        }
    }
    private fun onHowAppWorkClicked(){
        how_aap_works_btn.setOnClickListener {
            val intent = Intent(context, IntroWizardActivity::class.java)
            intent.putExtra("key",true)
            startActivity(intent)
        }
    }


    private fun onAboutUsClicked(){
        about_us_btn.setOnClickListener {
            val intent = Intent(context, AboutUsActivity::class.java)
            startActivity(intent)
        }
    }

    var mContext: MainActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as MainActivity
    }
    override fun onDetach() {
        super.onDetach()
        mContext = context as MainActivity
    }
}