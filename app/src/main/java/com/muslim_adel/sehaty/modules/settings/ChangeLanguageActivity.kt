package com.muslim_adel.sehaty.modules.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.register.LoginActivity
import com.muslim_adel.sehaty.modules.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_change_language.*

class ChangeLanguageActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_language)
        setSavetValus()
        onSaveClicked()

    }
    private fun setSavetValus(){
        if (preferences!!.getString("language","")=="Arabic"){
            lang_name_txt.text="عربي"
            ar_check.isChecked=true
            en_check.isChecked=false
        }else{
            lang_name_txt.text="عربي"
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

}