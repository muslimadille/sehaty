package com.muslim_adel.sehaty.modules.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.home.MainActivity
import com.muslim_adel.sehaty.modules.introSlider.IntroWizardActivity
import com.muslim_adel.sehaty.modules.register.RegisterationActivity
import com.muslim_adel.sehaty.utiles.Q
import kotlinx.android.synthetic.main.settings_activity.*

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        implementListeners()
        initLay()
    }
    private fun initLay(){
        if(!preferences!!.getBoolean(Q.IS_LOGIN,true)){
            edit_profile_btn.visibility=View.GONE
            edit_password_btn.visibility=View.GONE
        }else{
            edit_profile_btn.visibility=View.VISIBLE
            edit_password_btn.visibility=View.VISIBLE
        }

    }
    private fun implementListeners(){
        stn_language_btn.setOnClickListener {
            val intent = Intent(this@SettingsActivity, ChangeLanguageActivity::class.java)
            startActivity(intent)

        }
       
        edit_profile_btn.setOnClickListener {
            val intent = Intent(this@SettingsActivity, EditProfileActivity::class.java)
            startActivity(intent)
        }
        edit_password_btn.setOnClickListener {
            val intent = Intent(this@SettingsActivity, ChangePasswordActivity::class.java)
            startActivity(intent)
        }
    }

}