package com.example.finalproject_nepp

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.finalproject_nepp.databinding.ActivityEditAppointmentBinding
import java.text.SimpleDateFormat
import java.util.*

class EditAppointmentActivity : BaseActivity() {

    lateinit var binding: ActivityEditAppointmentBinding

//    약속 시간 일/시 를 저장해줄 Calendar. ( 월 값이 0 ~ 11로 움직이게 맞춰져 있다. )
    val mSelectedAppointmentDateTime = Calendar.getInstance() // 기본 값 : 현재 일시

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_appointment)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.txtDate.setOnClickListener {

            val dsl = object : DatePickerDialog.OnDateSetListener{
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
    }

    override fun setValues() {
    }
}