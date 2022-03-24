package com.example.finalproject_nepp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.finalproject_nepp.api.APIList
import com.example.finalproject_nepp.api.ServerAPI
import com.example.finalproject_nepp.databinding.ActivitySignInBinding

class SignInActivity : BaseActivity() {

    lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        apiList.postRequestLogin("","")
    }

    override fun setValues() {

    }
}