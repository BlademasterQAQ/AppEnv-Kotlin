package com.sollyu.android.appenv.commons

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.annotation.JSONField
import com.elvishew.xlog.XLog
import com.sollyu.android.appenv.bean.PhoneModel
import org.apache.commons.io.FileUtils
import java.io.File
import java.util.*

/**
 * 作者：sollyu
 * 时间：2017/12/8
 * 说明：
 */
class Phones {
    companion object {
        var Instance = Phones()

        fun Reload() {
            if (Instance.phoneFile.exists()) {
                val jsonObject = JSON.parseObject(FileUtils.readFileToString(Instance.phoneFile, "UTF-8"))
                XLog.d(jsonObject)
                Instance = JSON.toJavaObject(jsonObject, Phones::class.java)
            }
        }
    }

    @JSONField(name = "VersionName")
    var versionName = "1.0.1"

    @JSONField(name = "VersionCode")
    var versionCode = 1

    @JSONField(name = "PhoneList")
    var phoneList = HashMap<String, ArrayList<PhoneModel>>()

    @JSONField(serialize = false)
    val phoneFile = File(Application.Instance.getExternalFilesDir(null), "appenv.phone.json")

    @JSONField(serialize = false)
    fun save() {
        if (!Instance.phoneFile.parentFile.exists() && !Instance.phoneFile.parentFile.mkdirs()) return
        FileUtils.writeStringToFile(Instance.phoneFile, JSON.toJSONString(Instance, true), "UTF-8")
    }
}