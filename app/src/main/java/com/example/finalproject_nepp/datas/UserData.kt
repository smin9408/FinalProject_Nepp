package com.example.finalproject_nepp.datas

import java.io.Serializable

class UserData(
    val id: Int,
    val provider: String,
    val uid: String?,
    val email: String,
    val ready_minute: Int,
    val nick_name: String,
    val profile_img: String
) : Serializable {
}