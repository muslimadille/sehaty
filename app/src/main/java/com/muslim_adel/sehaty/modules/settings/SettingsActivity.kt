package com.muslim_adel.sehaty.modules.settings

import android.content.Intent
import android.os.Bundle
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.home.MainActivity
import com.muslim_adel.sehaty.modules.introSlider.IntroWizardActivity
import com.muslim_adel.sehaty.modules.register.RegisterationActivity
import kotlinx.android.synthetic.main.settings_activity.*

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        implementListeners()
    }
    private fun implementListeners(){
        stn_language_btn.setOnClickListener {
            val intent = Intent(this@SettingsActivity, ChangeLanguageActivity::class.java)
            startActivity(intent)

        }
        how_aap_works_btn.setOnClickListener {
            val intent = Intent(this@SettingsActivity, IntroWizardActivity::class.java)
            startActivity(intent)
        }
        edit_profile_btn.setOnClickListener {
            val intent = Intent(this@SettingsActivity, RegisterationActivity::class.java)
            startActivity(intent)
        }
        edit_password_btn.setOnClickListener {
            val intent = Intent(this@SettingsActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

}