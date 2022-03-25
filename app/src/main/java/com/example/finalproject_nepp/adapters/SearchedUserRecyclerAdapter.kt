package com.example.finalproject_nepp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_nepp.R
import com.example.finalproject_nepp.datas.UserData

class SearchedUserRecyclerAdapter(
    val mContext: Context,
    val mList: List<UserData>
) : RecyclerView.Adapter<SearchedUserRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgProfile = view.findViewById<ImageView>(R.id.imgProfile)
        val txtNickname = view.findViewById<TextView>(R.id.txtNickname)
        val imgSocialLoginLogo = view.findViewById<ImageView>(R.id.imgSocialLoginLogo)
        val txtEmail = view.findViewById<TextView>(R.id.txtEmail)
        val btnAddFriend = view.findViewById<Button>(R.id.btnAddFriend)

//        실 데이터 반영 기능이 있는 함수수
       fun bind(data: UserData){
            txtNickname.text = data.nick_name
            txtEmail.text = data.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

//        xml을 inflate해와서 => 이를 가지고, MyViewHolder 객체로 생성. 리턴.
//        재사용성을 알아서 보존해줌.

        val row = LayoutInflater.from(mContext).inflate(R.layout.searched_user_list_item, parent, false)

        return MyViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

//        실제 데이터 반영 함수
        val data = mList[position]

//        이 함수에서 직접 코딩하면 => holder.UI 변수로 매번 holder 단어를 써야함.
//        holder 변수의 멤버변수들을 사용할 수 있는 것처럼, 함수도 사용할 수 있다.

        holder.bind(data)

    }

//    몇 개의 아이템을 보여줄 예정인가? => 데이터 목록의 갯수만큼.
    override fun getItemCount(): Int {
        return mList.size
    }

}