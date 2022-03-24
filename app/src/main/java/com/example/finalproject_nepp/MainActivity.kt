package com.example.finalproject_nepp

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.finalproject_nepp.databinding.ActivityMainBinding
import com.example.finalproject_nepp.datas.BasicResponse
import com.example.finalproject_nepp.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
    }

    override fun setValues() {



//        GET - /user 접근해서, 내 정보 조회.
//        토큰값이 필요함. => 로그인 성공시 토큰 저장, ContextUtil에서 추출해서 사용.
//        apiList.getRequestMyInfo(ContextUtil.getLoginUserToken(mContext)).enqueue(object :Callback<BasicResponse>{
//            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
//                if(response.isSuccessful){
//                    val br = response.body()!!
//
//                    binding.txtUserNickname.text = br.data.user.nick_name
//                }
//            }
//
//            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
//            }
//        })

    }
}