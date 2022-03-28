package com.example.finalproject_nepp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.finalproject_nepp.fragments.AppointmentListFragment
import com.example.finalproject_nepp.fragments.MyProfileFragment

class MainViewPager2Adapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> AppointmentListFragment()
            else -> MyProfileFragment()
        }
    }
}