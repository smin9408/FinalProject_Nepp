package com.example.finalproject_nepp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject_nepp.R
import com.example.finalproject_nepp.api.APIList
import com.example.finalproject_nepp.api.ServerAPI
import com.example.finalproject_nepp.datas.BasicResponse
import com.example.finalproject_nepp.datas.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestUserRecyclerAdapter(
    val mContext: Context,
    val mList: List<UserData>
) : RecyclerView.Adapter<RequestUserRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgProfile = view.findViewById<ImageView>(R.id.imgProfile)
        val txtNickname = view.findViewById<TextView>(R.id.txtNickname)
        val txtEmail = view.findViewById<TextView>(R.id.txtEmail)
        val imgSocialLoginLogo = view.findViewById<ImageView>(R.id.imgSocialLoginLogo)
        val btnAccept = view.findViewById<Button>(R.id.btnAccept)
        val btnDeny = view.findViewById<Button>(R.id.btnDeny)

        fun bind(data: UserData) {

            Glide.with(mContext).load(data.profile_img).into(imgProfile)
            txtNickname.text = data.nick_name

//        사용자의 provider - "default" : 이메일 표시, 로그인하 sns에 따라 로고 변경
            when (data.provider) {
                "default" -> {
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

//            btnAccept, btnDeny 모두 같은 API를 호출. (하는 행동이 같다)
//            => type 파라미터에 첨부하는 값만 다름. ("수락" / "거절")

//            두 개의 버튼이 눌리면 할 일을 하나의 변수에 담아두자. (같은 일을 하게게)
//            할 일 : Interface => 정석 :     object : 인터페이스 종류 {  }
//            축약문법 (lambda) => 인터페이스종류{  }
            val ocl = View.OnClickListener{

//                서버에 수락 / 거절 의사 전달.
//                수락버튼 : 수락 / 거절버튼 : 거절 => 어느 버튼을 눌렀는지? 파악 가능해야, 파라미터도 다르게 전달.
//                it 변수 : 클릭된 버튼을 담고 있는 역할.
//                tag 속성 : 아무 말이나 적어도 되는 일종의 메모. 수락 / 거절 등 보내야 할 값을 메모 해두자.

                val tagStr = it.tag.toString()

                val retrofit = ServerAPI.getRetrofit(mContext)
                val apiList = retrofit.create(APIList::class.java)

                apiList.putRequestAcceptOrDenyFriendRequest(
                    data.id,
                    tagStr
                ).enqueue(object :Callback<BasicResponse>{
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    }
                })

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.requested_user_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val data = mList[position]
        holder.bind(data)

    }

    override fun getItemCount(): Int {
        return mList.size
    }
}