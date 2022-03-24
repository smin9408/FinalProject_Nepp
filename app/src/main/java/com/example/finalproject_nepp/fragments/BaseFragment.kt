package com.example.finalproject_nepp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.finalproject_nepp.api.APIList
import com.example.finalproject_nepp.api.ServerAPI

// 모든 프래그먼트의 공통 기능 추가.

abstract class BaseFragment : Fragment() {

    lateinit var mContext: Context

    lateinit var apiList: APIList

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mContext = requireContext() // 모든 화면 정보 > mContext를 대신 사용.

        val retrofit = ServerAPI.getRetrofit()
        apiList = retrofit.create(APIList::class.java)
    }

    abstract fun setupEvents()
    abstract fun setValues()

}