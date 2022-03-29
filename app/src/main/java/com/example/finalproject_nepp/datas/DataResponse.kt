package com.example.finalproject_nepp.datas

class DataResponse(
    val user: UserData,
    val token: String,

    val friends: List<UserData>,
    val users: List<UserData>,

    val appointments: List<AppointmentData>,

    val places: List<PlaceData>
) {
}