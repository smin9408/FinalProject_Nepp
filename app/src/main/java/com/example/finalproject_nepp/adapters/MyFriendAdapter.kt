package com.example.finalproject_nepp.adapters

import android.content.Context
import android.service.autofill.UserData
import android.widget.ArrayAdapter

class MyFriendAdapter(
    val mContext: Context,
    resId: Int,
    val mList: List<UserData>
) : ArrayAdapter<UserData>(mContext, resId, mList) {



}