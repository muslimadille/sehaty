package com.sehakhanah.patientapp.modules.settings

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.home.MainActivity
import com.muslim_adel.sehaty.modules.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_change_language.*

class ChangeLanguageActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_language)
        initBottomNavigation()
        setSavetValus()
        onSaveClicked()

    }
    private fun setSavetValus(){
        if (preferences!!.getString("language","")=="Arabic"){
            lang_name_txt.text="عربي"
            ar_check.isChecked=true
            en_check.isChecked=false
        }else{
            lang_name_txt.text="English"
            ar_check.isChecked=false
            en_check.isChecked=true
        }
        ar_check.setOnClickListener {
            lang_name_txt.text="عربي"
            ar_check.isChecked=true
            en_check.isChecked=false
        }
        en_check.setOnClickListener {
            lang_name_txt.text="English"
            ar_check.isChecked=false
            en_check.isChecked=true
        }

    }
    private fun onSaveClicked(){
        lang_save_btn.setOnClickListener {
            if(ar_check.isChecked){
                preferences!!.putString("language", "Arabic")
                preferences!!.commit()
                val intent = Intent(this@ChangeLanguageActivity, SplashActivity::class.java)
                startActivity(intent)
                finish()
            }else if(en_check.isChecked){
                preferences!!.putString("language", "English")
                preferences!!.commit()
                val intent = Intent(this@ChangeLanguageActivity, SplashActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
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
        bottomNavigationView17.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView17.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
}