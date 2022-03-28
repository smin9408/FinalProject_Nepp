package com.example.finalproject_nepp

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.finalproject_nepp.adapters.MainViewPager2Adapter
import com.example.finalproject_nepp.adapters.MainViewPagerAdapter
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
    }

    override fun setValues() {

        binding.mainViewPager2.adapter = MainViewPager2Adapter( this ) // 변수 : Activity => 객체 : Context로 대입 불가.




    }
}