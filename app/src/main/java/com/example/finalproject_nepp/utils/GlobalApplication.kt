package com.example.finalproject_nepp.utils

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "f8e922f62c0944aceaa25dcea5b4ad25")
    }


}