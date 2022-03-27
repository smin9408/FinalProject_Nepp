package com.example.finalproject_nepp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.finalproject_nepp.fragments.AppointmentListFragment
import com.example.finalproject_nepp.fragments.MyFriendsFragment
import com.example.finalproject_nepp.fragments.MyProfileFragment
import com.example.finalproject_nepp.fragments.RequestedUsersFragment

class FriendViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MyFriendsFragment()
            else -> RequestedUsersFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "약속 목록"
            else -> "내 프로필"
        }
    }
}