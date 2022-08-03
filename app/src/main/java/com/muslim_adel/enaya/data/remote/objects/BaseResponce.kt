package com.muslim_adel.enaya.data.remote.objects

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BaseResponce<T> : Serializable{
    var success: Boolean =false
    var message: Any? = null
    var data: T? = null
}
