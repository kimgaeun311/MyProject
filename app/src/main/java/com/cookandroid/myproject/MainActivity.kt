package com.cookandroid.myproject

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cookandroid.myproject.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.ArrayList
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var goal_plus : ImageView
    lateinit var modify_button: Button
    lateinit var delete_button: Button
    lateinit var plus_button: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "좀좀일기"

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        CalendarUtill.selectedDate = LocalDate.now()

        setMonthView()

        binding.preBtn.setOnClickListener {

            CalendarUtill.selectedDate = CalendarUtill.selectedDate.minusMonths(1)
            setMonthView()
        }

        binding.nextBtn.setOnClickListener {
            CalendarUtill.selectedDate = CalendarUtill.selectedDate.plusMonths(1)
            setMonthView()
        }

        goal_plus= findViewById(R.id.goal_plus)
        modify_button=findViewById(R.id.modify_button)
        delete_button=findViewById(R.id.delete_button)
        plus_button=findViewById(R.id.plus_button)


        modify_button.setOnClickListener {
            Toast.makeText(this, "해당 좀좀일기가 수정되었습니다.", Toast.LENGTH_SHORT).show()
        }

        delete_button.setOnClickListener {
            Toast.makeText(this, "해당 좀좀일기가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
        }

        plus_button.setOnClickListener {
            Toast.makeText(this, "해당 좀좀일기가 등록되었습니다.", Toast.LENGTH_SHORT).show()
        }

        goal_plus.setOnClickListener {
            val intent = Intent(this, SettingGoal::class.java)
            startActivity(intent)
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView(){
        binding.monthYearText.text = monthYearFromDate(CalendarUtill.selectedDate)

        val dayList = dayInMonthArray(CalendarUtill.selectedDate)

        val adapter = CalendarAdapter(dayList)

        var manager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)

        binding.recyclerView.layoutManager = manager

        binding.recyclerView.adapter = adapter

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun monthYearFromDate(date: LocalDate):String{

        var formatter = DateTimeFormatter.ofPattern("MM월 yyyy")
        return date.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dayInMonthArray(date: LocalDate): ArrayList<LocalDate?>{

        var dayList = ArrayList<LocalDate?>()

        var yearMonth = YearMonth.from(date)

        var lastDay = yearMonth.lengthOfMonth()

        var firstDay = CalendarUtill.selectedDate.withDayOfMonth(1)

        var dayOfWeek = firstDay.dayOfWeek.value

        for(i in 1..41){
            if(i <= dayOfWeek || i > (lastDay + dayOfWeek)){
                dayList.add(null)
            }else{
                dayList.add(LocalDate.of(CalendarUtill.selectedDate.year, CalendarUtill.selectedDate.monthValue, i-dayOfWeek))
            }
        }

        return dayList
    }

}