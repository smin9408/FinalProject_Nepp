package com.example.finalproject_nepp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_nepp.R
import com.example.finalproject_nepp.datas.UserData

class SearchedUserRecyclerAdapter(
    val mContext: Context,
    val mList: List<UserData>
) : RecyclerView.Adapter<SearchedUserRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

//        xml을 inflate해와서 => 이를 가지고, MyViewHolder 객체로 생성. 리턴.
//        재사용성을 알아서 보존해줌.

        val row = LayoutInflater.from(mContext).inflate(R.layout.searched_user_list_item, parent, false)

        return MyViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

//        실제 데이터 반영 함수

    }

//    몇 개의 아이템을 보여줄 예정인가? => 데이터 목록의 갯수만큼.
    override fun getItemCount(): Int {
        return mList.size
    }

}