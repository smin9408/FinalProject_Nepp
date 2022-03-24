package com.example.finalproject_nepp

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.finalproject_nepp.databinding.ActivitySignUpBinding
import com.example.finalproject_nepp.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnEmailCheck.setOnClickListener {

            val inputEmail = binding.edtEmail.text.toString()

            apiList.getRequestDuplicatedCheck("EMAIL", inputEmail).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(mContext, "사용 가능한 이메일 입니다.", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mContext, "중복된 이메일 입니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })



        }

        binding.btnSignUp.setOnClickListener {

            val inputEmail = binding.edtEmail.text.toString()
            val inputPw = binding.edtPassword.text.toString()
            val inputNickname = binding.edtNickname.text.toString()

//            회원가입 API 호출
            apiList.putRequestSignUp(inputEmail, inputPw, inputNickname).enqueue(object :Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(mContext, "회원가입에 성공했습니다!", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                }
            })

        }

    }

    override fun setValues() {
    }
}