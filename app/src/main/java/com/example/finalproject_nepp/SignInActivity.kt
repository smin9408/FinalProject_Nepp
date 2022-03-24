package com.example.finalproject_nepp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.finalproject_nepp.api.APIList
import com.example.finalproject_nepp.api.ServerAPI
import com.example.finalproject_nepp.databinding.ActivitySignInBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : BaseActivity() {

    lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnLogin.setOnClickListener {

            val inputEmail = binding.edtEmail.text.toString()
            val inputPassword = binding.edtPassword.text.toString()

            apiList.postRequestLogin(inputEmail, inputPassword).enqueue(object :Callback<JSONObject>{
                override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {


                    if (response.isSuccessful){

                    }
                }

                override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                }
            })

        }
    }

    override fun setValues() {

    }
}