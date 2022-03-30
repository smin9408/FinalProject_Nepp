package com.example.finalproject_nepp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.finalproject_nepp.databinding.ActivitySignInBinding
import com.example.finalproject_nepp.datas.BasicResponse
import com.example.finalproject_nepp.utils.ContextUtil
import com.kakao.sdk.user.UserApiClient
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

        binding.btnKakaoLogin.setOnClickListener {

//            카톡 로그인 기능 실행

//            카톡 앱 로그인이 가능한지?
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(mContext)){

//                카톡 앱이 설치되어있는 상황
                UserApiClient.instance.loginWithKakaoTalk(mContext) {token, error ->

                    Log.d("카카오 로그인", "카톡 앱으로 로그인")
                }
            }
            else{
//                카톡 앱이 없는 상황. 로그인 창 띄워주기
                UserApiClient.instance.loginWithKakaoAccount(mContext){token, error ->

                    Log.d("카카오 로그인", "카톡 앱 없이 로그인")
                }
            }
        }

        binding.btnSignUp.setOnClickListener {


            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)

        }

        binding.btnLogin.setOnClickListener {

            val inputEmail = binding.edtEmail.text.toString()
            val inputPassword = binding.edtPassword.text.toString()

            apiList.postRequestLogin(inputEmail, inputPassword).enqueue(object :Callback<BasicResponse>{
                override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {


                    if (response.isSuccessful){

                        val br = response.body()!!

                        Toast.makeText(mContext,"${br.data.user.nick_name}님, 환영합니다!!",Toast.LENGTH_SHORT).show()

//                        서버가 내려주는 토큰값을 저장.
                        ContextUtil.setLoginUserToken(mContext, br.data.token)


//                        메인화면으로 이동
                        val myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)

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