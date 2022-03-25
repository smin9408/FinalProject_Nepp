package com.example.finalproject_nepp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.finalproject_nepp.databinding.ActivitySearchUserBinding
import com.example.finalproject_nepp.datas.BasicResponse
import com.example.finalproject_nepp.datas.UserData
import com.example.finalproject_nepp.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserActivity : BaseActivity() {

    lateinit var binding: ActivitySearchUserBinding

    val mSearchedUserList = ArrayList<UserData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_user)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnSearch.setOnClickListener {

            val inputkeyword = binding.edtNickname.text.toString()

            apiList.getRequestSearchUser(ContextUtil.getLoginUserToken(mContext), inputkeyword)
                .enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {

                        if (response.isSuccessful) {

                            val br = response.body()!!

                            mSearchedUserList.addAll(br.data.users)
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