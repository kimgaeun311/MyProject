package com.cookandroid.myproject

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.util.ArrayList

class CalendarAdapter(private val dayList: ArrayList<LocalDate?>): RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {

    //캘린더 일수 구현과 레이아웃 구현 기능
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val dayText: TextView = itemView.findViewById(R.id.dayText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_cell, parent, false)

        return ItemViewHolder(view)
    }

    //데이터설정

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        //날짜 변수
        var day = dayList[holder.adapterPosition]

        if(day==null){
            holder.dayText.text = ""
        }else{
            holder.dayText.text =day.dayOfMonth.toString()

            if(day==CalendarUtill.selectedDate){
                holder.itemView.setBackgroundColor(Color.LTGRAY)
            }
        }

        //날짜 클릭
        holder.itemView.setOnClickListener{

            var iYear = day?.year
            var iMonth = day?.monthValue
            var iDay = day?.dayOfMonth


            var yearMonDay = "$iYear 년 $iMonth 월 $iDay 일"


            if(day==CalendarUtill.selectedDate){
                dayChange(holder.itemView.context)
            }
            Toast.makeText(holder.itemView.context, yearMonDay, Toast.LENGTH_SHORT).show()


        }
    }


    override fun getItemCount(): Int {

        return dayList.size
    }


    fun dayChange(context: Context) {

        val intent = Intent(context, dayClickActivity::class.java)
        context.startActivities(arrayOf(intent))
    }


}