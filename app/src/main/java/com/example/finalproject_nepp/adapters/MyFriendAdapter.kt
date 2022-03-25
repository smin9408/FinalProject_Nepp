package com.example.finalproject_nepp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.finalproject_nepp.R
import com.example.finalproject_nepp.datas.UserData

class MyFriendAdapter(
    val mContext: Context,
    resId: Int,
    val mList: List<UserData>
) : ArrayAdapter<UserData>(mContext, resId, mList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView
        if (tempRow == null) {
            tempRow = LayoutInflater.from(mContext).inflate(R.layout.my_friend_list_item, null)
        }

        val row = tempRow!!

        val data = mList[position]

        val imgProfile = row.findViewById<ImageView>(R.id.imgProfile)
        val txtNickname = row.findViewById<TextView>(R.id.txtNickname)
        val txtEmail = row.findViewById<TextView>(R.id.txtEmail)
        val imgSocialLoginLogo = row.findViewById<ImageView>(R.id.imgSocialLoginLogo)

        Glide.with(mContext).load(data.profile_img).into(imgProfile)
        txtNickname.text = data.nick_name

//        사용자의 provider - "default" : 이메일 표시, 로그인하 sns에 따라 로고 변경
        when (data.provider) {
            "defalut" -> {
//                이메일 표사
                txtEmail.text = data.email

//                로고 이미지 숨김
                imgSocialLoginLogo.visibility = View.GONE
            }
            "kakao" -> {
                txtEmail.text = "카카오 로그인"
                imgSocialLoginLogo.visibility = View.VISIBLE
                Glide.with(mContext).load(R.drawable.kakao_logo).into(imgSocialLoginLogo)
            }
            "facebook" -> {
                txtEmail.text = "페북 로그인"
                imgSocialLoginLogo.visibility = View.VISIBLE
                Glide.with(mContext).load(R.drawable.facebook_logo).into(imgSocialLoginLogo)
            }
            "naver" -> {
                txtEmail.text = "네이버 로그인"
                imgSocialLoginLogo.visibility = View.VISIBLE

//               Glide는 웹의 이미지 뿐 아니라, 우리 프로젝트 내부의 이미지도 불러낼 수 있다.
                Glide.with(mContext).load(R.drawable.naver_logo).into(imgSocialLoginLogo)
            }
            else -> {
//                그 외의 잘못된 경우
            }
        }

        return row
    }

}