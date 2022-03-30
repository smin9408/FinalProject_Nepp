package com.example.finalproject_nepp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.finalproject_nepp.adapters.StartPlaceSpinnerAdapter
import com.example.finalproject_nepp.databinding.ActivityEditAppointmentBinding
import com.example.finalproject_nepp.datas.BasicResponse
import com.example.finalproject_nepp.datas.PlaceData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EditAppointmentActivity : BaseActivity() {

    lateinit var binding: ActivityEditAppointmentBinding

    //    약속 시간 일/시 를 저장해줄 Calendar. ( 월 값이 0 ~ 11로 움직이게 맞춰져 있다. )
    val mSelectedAppointmentDateTime = Calendar.getInstance() // 기본 값 : 현재 일시

    //    약속 장소 관련 멤버변수
    var marker: Marker? = null // 지도에 표시될 하나의 마커.
    var path: PathOverlay? = null // 출발지 ~ 도착지까지 보여줄 경로 선. 처음에는 보이지 않는 상태.

    var mSelectedLatLng: LatLng? = null // 약속 장소 위/경도 도 처음에는 설정하지 않음

    var naverMap: NaverMap? = null

    //    내 출발 장소 목록
    val mStartPlaceList = ArrayList<PlaceData>()
    lateinit var mStartPlaceAdapter: StartPlaceSpinnerAdapter

    //    선택한 출발 장소
    var mSelectedStartPlace: PlaceData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_appointment)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.startPlaceSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {

//                몇번째 아이템이 선택 되었는지, p2 or position가 알려줌.

                    mSelectedStartPlace = mStartPlaceList[position]

//                선택한 출발지 ~ 지도에서 클릭한 도착지까지의 이동 경로 / 교통 정보 표현.


                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

//        스크롤 보조용 텍스트뷰에 손이 닿으면 => 스크롤뷰의 이벤트 일시정지. (지도만 움직이게)
        binding.txtScrollHelp.setOnTouchListener { view, motionEvent ->

//            스크롤뷰의 이벤트 정지
            binding.scrollView.requestDisallowInterceptTouchEvent(true)

//            텍스트뷰의 터치이벤트만? false. => 뒤에 가려져있는 지도도 터치를 허용해줘야함.
            return@setOnTouchListener false

        }

//        저장 버튼이 눌리면
        binding.btnSave.setOnClickListener {

//            입력 값 들이 제대로 되어있는지? 확인 => 잘못되었다면 막아주자. (input validation)
            val inputTitle = binding.edtTitle.text.toString()

//            제목을 입력하지 않았다면 거부.(예시)
            if (inputTitle.isEmpty()) {
                Toast.makeText(mContext, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            시간을 선택하지 않았다면 막자.
//            기준? txtDate, txtTime 두개의 문구 중 하나라도 처음 문구 그대로면 입력 안했다고 간주.
            if (binding.txtDate.text == "약속 일자") {
                Toast.makeText(mContext, "일자를 선택 해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.txtTime.text == "약속 시간") {
                Toast.makeText(mContext, "시간을 선택 해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            선택한 일시가 , 지금보다 이전의 일시라면 "현재 이후의 시간으로 선택해주세요."
            val now = Calendar.getInstance() // 저장 버튼을 누른 현재 시간.
            if (mSelectedAppointmentDateTime.timeInMillis < now.timeInMillis) {
                Toast.makeText(mContext, "현재 이후의 시간으로 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            장소 이름도 반드시 입력하게.
            val inputPlaceName = binding.edtPlaceName.text.toString()
            if (inputPlaceName.isEmpty()) {
                Toast.makeText(mContext, "장소 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


//            장소를 선택했는지? 안했다면 등록 거부.
            if (mSelectedLatLng == null) {
                Toast.makeText(mContext, "약속 장소를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            지금 선택되어 있는 아이템이 몇번째 아이템인지
            val selectedStartPlace = mStartPlaceList[binding.startPlaceSpinner.selectedItemPosition]


//            약속일시 - yyyy-MM-dd HH:mm 양식을 서버가 지정해서 요청.
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")

            apiList.postRequestAddAppointment(
                inputTitle,
                sdf.format(mSelectedAppointmentDateTime.time),
                selectedStartPlace.name,
                selectedStartPlace.latitude,
                selectedStartPlace.longitude,
                inputPlaceName,
                mSelectedLatLng!!.latitude,
                mSelectedLatLng!!.longitude
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(mContext, "약속을 등록했습니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                }
            })

        }

//        날짜 선택 텍스트뷰 클릭 이벤트 - DataPickerDialog
        binding.txtDate.setOnClickListener {

            val dsl = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

//                    연 / 월 / 일은, JAVA / Kotlin 언어의 기준 (0~11) 으로 월 값을 줌.
//                    주는 그대로 Calendar에 set 하게되면, 올바른 월로 세팅됨.

                    mSelectedAppointmentDateTime.set(year, month, dayOfMonth) // 연월일 한번에 세팅 함수.

//                    약속일자의 문구를 22/03/28 등의 형식으로 바꿔서 보여주자.
//                    SimpleDateFormat을 활용 => 월 값도 알아서 보정.

                    val sdf = SimpleDateFormat("yy-MM-dd")

                    binding.txtDate.text = sdf.format(mSelectedAppointmentDateTime.time)

                }

            }

            val dpd = DatePickerDialog(
                mContext,
                dsl,
                mSelectedAppointmentDateTime.get(Calendar.YEAR),
                mSelectedAppointmentDateTime.get(Calendar.MONTH),
                mSelectedAppointmentDateTime.get(Calendar.DAY_OF_MONTH)
            ).show()

        }


//        시간 선택 텍스트뷰 클릭 이벤트 - TimePickerDialog
        binding.txtTime.setOnClickListener {

            val tsl = object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {

//                    약속 일시의 시간으로 설정.
                    mSelectedAppointmentDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    mSelectedAppointmentDateTime.set(Calendar.MINUTE, minute)

                    val sdf = SimpleDateFormat("a h시 m분")
                    binding.txtTime.text = sdf.format(mSelectedAppointmentDateTime.time)
                }

            }

            val tpd = TimePickerDialog(
                mContext,
                tsl,
                18,
                0,
                false
            ).show()

        }
    }

    override fun setValues() {

//        네이버 지도 객체 얻어오기 => 얻어와지면 할 일 (Interface) 코딩
        binding.naverMapView.getMapAsync {

//            지도 로딩이 끝나고 난 후에 얻어낸 온전한 지도 객체
            naverMap = it

            setNaverMap()


        }


//        내 출발장소 목록 불러오기
        getMyStartPlaceListFromServer()

//        스피너 어댑터 연결 -> 리스트뷰와 동일함.
        mStartPlaceAdapter = StartPlaceSpinnerAdapter(
            mContext,
            R.layout.start_place_spinner_list_item,
            mStartPlaceList
        )
        binding.startPlaceSpinner.adapter = mStartPlaceAdapter

    }

    fun setNaverMap() {

//        로딩이 끝난 네이버 지도를 가지고 실행할 코드.

//        출발지점이 선택 되어야 세팅 진행.
        if (mSelectedStartPlace == null) {
            return // 우선 함수 강제 종료.
        }

        //            지도 시작지점 : 선택된 좌표
        val coord = LatLng(mSelectedStartPlace!!.latitude, mSelectedStartPlace!!.longitude)

//            coord 에 설정한 좌표로 > 네이버지도의 카메라 이동.
        val cameraUpdate = CameraUpdate.scrollTo(coord)
        naverMap!!.moveCamera(cameraUpdate)

//            첫 마커 좌표 -> null
//            val marker = Marker() => 멤버변수로 하나의 마커만 만들어서 관리하자.
//            marker = Marker()
//            marker!!.position = coord
//            marker!!.map = naverMap

//            처음 선택된 좌표 -> null
//            mSelectedLatLng = coord

//            지도 클릭 이벤트
        naverMap!!.setOnMapClickListener { pointF, latLng ->

//                (찍혀있는 마커가 없다면) 마커를 새로 추가
            if (marker == null) {
                marker = Marker()
            }

//                 그 마커의 위치 / 지도 적용
            marker!!.position = latLng
            marker!!.map = naverMap

//                약속 장소도 새 좌표로 설정.
            mSelectedLatLng = latLng

//                coord ~ 선택한 latLng 까지 직선을 그려보자. (PathOverlay 기능 활용) + ODsay 라이브러리 활용


//                ArrayList를 만들어서, 출발지와 도착지를 추가.
            val coordList = ArrayList<LatLng>()

            coordList.add(coord)
            coordList.add(latLng) // 클릭된 좌표 추가

            path!!.coords = coordList

            path!!.map = naverMap


//            길찾기 API 실행
            findWay()

        }

    }

    //    길찾기 관련 코드를 별도 함수로. => 여러곳에서 활용 가능.
    fun findWay() {
        val myODsayService =
            ODsayService.init(mContext, "Nk0imGVQqJfTmLYgdWfR8iep+hE0Ft6KcIi3x9v1SPY")
        myODsayService.requestSearchPubTransPath(
            mSelectedStartPlace!!.longitude.toString(),
            mSelectedStartPlace!!.latitude.toString(),
            mSelectedStartPlace!!.longitude.toString(),
            mSelectedStartPlace!!.latitude.toString(),
            null,
            null,
            null,
            object : OnResultCallbackListener {
                override fun onSuccess(p0: ODsayData?, p1: API?) {

                    val jsonObj = p0!!.json!!

                    val resultObj = jsonObj.getJSONObject("result")

                    val pathArr = resultObj.getJSONArray("path") // 여러 추천 경로 중 첫번째 만 사용해보자.

                    val firstPathObj = pathArr.getJSONObject(0) // 0번째 경로 추출.

//                           첫번째 경로를 지나는 모든 정거장들의 위경도 값을 담을 목록
                    val stationLatLngList = ArrayList<LatLng>()

//                            출발지 좌표를 정거장 목록에 먼저 추가
                    stationLatLngList.add(
                        LatLng(
                            mSelectedStartPlace!!.latitude,
                            mSelectedStartPlace!!.longitude
                        )
                    )

//                            출발지 ~ 도착지 : 도보 5분 / 지하철 30분 / 버스 30분 / 도보 5분
                    val subPathArr = firstPathObj.getJSONArray("subPath")

                    for (i in 0 until subPathArr.length()) {

                        val subPathObj = subPathArr.getJSONObject(i)

//                                둘러보려는 경로가, 정거장 목록을 내려준다면 (지하철 or 버스) => 내부 파싱
                        if (!subPathObj.isNull("passStopList")) {

                            val passStopListObj = subPathObj.getJSONObject("passStopList")
                            val stationsArr = passStopListObj.getJSONArray("stations")


//                                    실제 정거장 목록 파싱 => 각 정거장의 위도/경도 추출 가능. => ArrayList에 담아서, 경로선의 좌표로 활용
                            for (j in 0 until stationsArr.length()) {

                                val stationObj = stationsArr.getJSONObject(j)

//                                        위도(y좌표), 경도(x좌표) 추출
                                val lat = stationObj.getString("y").toDouble()
                                val lng = stationObj.getString("x").toDouble()

//                                        네이버 지도의 좌표로 만들어서 > ArrayList에 담자.
                                stationLatLngList.add(LatLng(lat, lng))
                            }
                        }
                    }

//                            최종 정거장 ~ 도착지 까지 직선
                    stationLatLngList.add(mSelectedLatLng!!)

//                            완성된 정거장 경로들을 => path의 경로로 재설정. 지도에 새로 반영.
                    path!!.coords = stationLatLngList
                    path!!.map = naverMap

//                            (첫번째 추천 경로의) 정보 항목도 파싱.
//                            예상 소요 시간 파싱 => 임시로 토스트 출력.
                    val infoObj = firstPathObj.getJSONObject("info")
                    val totalTime = infoObj.getInt("totalTime") // 소요 시간
                    val payment = infoObj.getInt("payment") // 소요 비용

//                            네이버 지도 라이브러리의 InfoWindow 기능 활용.
                    val infoWindow = InfoWindow()
                    infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(mContext) {
                        override fun getText(p0: InfoWindow): CharSequence {
                            return "이동시간 : ${totalTime}분, 비용 : ${payment}원"
                        }

                    }

                    infoWindow.open(marker!!)

                    marker!!.setOnClickListener {

                        if (marker!!.infoWindow == null) {
                            infoWindow.open(marker!!)
                        } else {
                            infoWindow.close()
                        }

                        return@setOnClickListener true
                    }

//                            키메라를 latlng (클릭한 위치) 가운데로 오도록
                    val cameraUpdate = CameraUpdate.scrollTo(mSelectedLatLng!!)
                    naverMap!!.moveCamera(cameraUpdate)


                }

                override fun onError(p0: Int, p1: String?, p2: API?) {
                }

            }
        )

        if (path == null) {
            path = PathOverlay()
        }
    }

    fun getMyStartPlaceListFromServer() {

        apiList.getRequestMyPlaceList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    val br = response.body()!!

                    mStartPlaceList.clear()

                    mStartPlaceList.addAll(br.data.places)

                    mStartPlaceAdapter.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })

    }
}