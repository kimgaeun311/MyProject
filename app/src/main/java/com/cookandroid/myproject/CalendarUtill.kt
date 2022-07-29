package com.cookandroid.myproject

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

//캘린더
class CalendarUtill {
    companion object{
        @RequiresApi(Build.VERSION_CODES.O)
        var selectedDate: LocalDate = LocalDate.now()
    }
}