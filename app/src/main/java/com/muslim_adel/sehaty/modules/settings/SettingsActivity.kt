package com.muslim_adel.sehaty.modules.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.home.MainActivity
import com.muslim_adel.sehaty.modules.introSlider.IntroWizardActivity
import com.muslim_adel.sehaty.modules.register.RegisterationActivity
import com.muslim_adel.sehaty.utiles.Q
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.settings_activity.*

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        initBottomNavigation()
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
        bottomNavigationView20.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView20.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
}