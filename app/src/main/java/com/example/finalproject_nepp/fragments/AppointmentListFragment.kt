package com.example.finalproject_nepp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.finalproject_nepp.EditAppointmentActivity
import com.example.finalproject_nepp.R
import com.example.finalproject_nepp.databinding.FragmentAppointmentListBinding
import com.example.finalproject_nepp.datas.AppointmentData
import com.example.finalproject_nepp.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentListFragment : BaseFragment() {

    lateinit var binding: FragmentAppointmentListBinding

    val mAppointmentList = ArrayList<AppointmentData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointment_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()


    }

    override fun setupEvents() {

        binding.btnAddAppointment.setOnClickListener {

            val myIntent = Intent(mContext, EditAppointmentActivity::class.java)
            startActivity(myIntent)
        }

    }

    override fun setValues() {
        getMyAppointmentListFromServer()
    }

    fun getMyAppointmentListFromServer(){

        apiList.getRequestAppointmentList().enqueue(object :Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if(response.isSuccessful){
                    val br = response.body()!!

                    mAppointmentList.addAll(br.data.appointments)
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })

    }

}