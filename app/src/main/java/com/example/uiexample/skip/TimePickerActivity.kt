package com.example.uiexample.skip

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import com.example.uiexample.BaseActivity
import com.example.uiexample.R
import kotlinx.android.synthetic.main.activity_time_picker.*
import java.lang.StringBuilder
import java.util.*

class TimePickerActivity : BaseActivity(), TimePickerDialog.OnTimeSetListener {
    private val selectDate: StringBuilder = StringBuilder()
    private val selectTime: StringBuilder = StringBuilder()

    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var timePickerDialog: TimePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.init("选择日期时间")
        initView()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_time_picker
    }

    private fun initView() {
        initPicker()
        select_date.setOnClickListener {
            datePickerDialog.show()
        }
        select_time.setOnClickListener {
            timePickerDialog.show()
        }
        handleSelect()
    }

    private fun initPicker() {
        val currentDate = Calendar.getInstance()
        currentDate.timeInMillis = System.currentTimeMillis()
        datePickerDialog = DatePickerDialog(this)
        datePickerDialog.datePicker.init(currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)) {view, year, month, day ->
            selectDate.clear()
            selectDate.append("${year}年")
            selectDate.append("${month}月")
            selectDate.append("${day}日")
            handleSelect()
        }

        timePickerDialog = TimePickerDialog(this, this,
            currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true)
    }

    private fun handleSelect() {
        show_result.setText("选择的时间: ${selectDate}${selectTime}")
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        selectTime.append("${hourOfDay}时")
        selectTime.append("${minute}分")
        handleSelect()
    }

    override fun setExtras() {
        extras.putString("from_where", TIME_PICKER)
        extras.putString("date", selectDate.toString())
        extras.putString("time", selectTime.toString())
    }
}