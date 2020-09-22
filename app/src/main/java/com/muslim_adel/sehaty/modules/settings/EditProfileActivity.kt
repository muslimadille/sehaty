package com.muslim_adel.sehaty.modules.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.utiles.Q
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        setcurrentdata()
    }

    private fun setcurrentdata() {
        username.setText(preferences!!.getString(Q.USER_NAME, ""), TextView.BufferType.EDITABLE)
        phon_num.setText(preferences!!.getString(Q.USER_PHONE, ""), TextView.BufferType.EDITABLE)
        email.setText(preferences!!.getString(Q.USER_EMAIL, ""), TextView.BufferType.EDITABLE)
        date_of_birth.setText(
            preferences!!.getString(Q.USER_BIRTH, ""),
            TextView.BufferType.EDITABLE
        )
        if (preferences!!.getInteger(Q.USER_GENDER, -1) == 1) {
            mail_btn.isChecked=true
            femail_btn.isChecked=false
        }else{
            mail_btn.isChecked=false
            femail_btn.isChecked=true
        }

    }
}