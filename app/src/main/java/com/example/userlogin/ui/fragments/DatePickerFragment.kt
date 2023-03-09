package com.example.userlogin.ui.activities

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.userlogin.R
import java.util.*


class DatePickerActivity(context: Context) : AppCompatActivity() {
    private val mContext = context

    interface OnDateSelectedListener {
        fun onDateSelected(selectedDate: String?)
    }

    private var mDateListener: OnDateSelectedListener? = null
    fun setOnDateSelectedListener(listener: OnDateSelectedListener) {
        mDateListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_picker)

    }

    fun showDatePicker() {
        val c = Calendar.getInstance()
        val cYear = c.get(Calendar.YEAR)
        val cMonth = c.get(Calendar.MONTH)
        val cDay = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(
            mContext,
            R.style.SpinnerDatePickerStyle,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val month = selectedMonth + 1 // Tháng bắt đầu từ 0, cần cộng thêm 1 để đúng tháng
                val date = String.format("%02d.%02d.%04d", selectedDayOfMonth, month, selectedYear)
                mDateListener?.onDateSelected(date)
            },
            cYear,
            cMonth,
            cDay
        )

        // Tạo một đối tượng DatePicker và thiết lập nó cho DatePickerDialog
        val datePicker = DatePicker(mContext)
        datePicker.calendarViewShown = false
        dpd.setView(datePicker)


        // Hiển thị DatePickerDialog
        dpd.show()

    }
}