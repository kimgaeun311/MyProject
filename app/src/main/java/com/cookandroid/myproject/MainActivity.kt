package com.cookandroid.myproject

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.cookandroid.myproject.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var selectedDate: LocalDate

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "좀좀일기"

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        selectedDate = LocalDate.now()

        setMonthView()

        binding.preBtn.setOnClickListener {
            selectedDate = selectedDate.minusMonths(1)
            setMonthView()
        }

        binding.nextBtn.setOnClickListener {
            selectedDate = selectedDate.plusMonths(1)
            setMonthView()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView(){
        binding.monthYearText.text = monthYearFromDate(selectedDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun monthYearFromDate(date: LocalDate):String{

        var formatter = DateTimeFormatter.ofPattern("MM월 yyyy")
        return date.format(formatter)
    }

}