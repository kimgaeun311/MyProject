package com.cookandroid.myproject

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
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
    lateinit var dateEditText: EditText
    lateinit var diaryEditText: EditText
    lateinit var goal_plus : ImageView
    lateinit var diary_search:ImageView
    lateinit var modify_button: Button
    lateinit var delete_button: Button
    lateinit var plus_button: Button
    lateinit var myHelper:myDBHelper

    lateinit var spinner2: Spinner
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var dbManager: DBManager
    lateinit var sqlDB: SQLiteDatabase


    @SuppressLint("Range")
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
        diary_search=findViewById(R.id.diary_search)
        modify_button=findViewById(R.id.modify_button)
        delete_button=findViewById(R.id.delete_button)
        plus_button=findViewById(R.id.plus_button)
        dateEditText=findViewById(R.id.dateEditText)
        diaryEditText=findViewById(R.id.diaryEditText)
        myHelper = myDBHelper(this)

        //좀좀목표를 추가하는 아이콘을 클릭한 경우
        goal_plus.setOnClickListener {
            val intent = Intent(this, SettingGoal::class.java)
            startActivity(intent)
        }

        //연월일 정보(YYYY/MM/DD)를 입력한 후 일기 조회 버튼을 클릭한 경우
        diary_search.setOnClickListener {
            //입력된 연월일 정보를 갖고 DB에서 일기 내용 검색
            sqlDB = myHelper.readableDatabase
            var cursor: Cursor
            cursor = sqlDB.rawQuery("SELECT * FROM diaryTBL WHERE dateText='"+dateEditText.text.toString()+"';", null)
            var diary_toast:String

            //DB에 해당 날짜와 그에 해당하는 일기 내용이 있는 경우
            if (cursor.moveToNext()){
                diary_toast=cursor.getString(1)
                diaryEditText.setText(diary_toast)
                Toast.makeText(this, "해당 날짜의 일기 조회 완료", Toast.LENGTH_SHORT).show()
            }
            else{ //DB에 해당 날짜 관련 데이터가 없는 경우

                diaryEditText.setText("(해당 날짜의 일기 없음)")
            }
            cursor.close()
            sqlDB.close()
        }

//좀좀일기-수정하기 버튼을 클릭한 경우
        modify_button.setOnClickListener {
            sqlDB=myHelper.writableDatabase
            //해당 날짜의 일기 내용 수정(업데이트)
            sqlDB.execSQL("UPDATE diaryTBL SET diaryText= '"+diaryEditText.text+"' WHERE dateText = '"+
                    dateEditText.text.toString()+"';")
            sqlDB.close()
            Toast.makeText(this, "해당 좀좀일기가 수정되었습니다.", Toast.LENGTH_SHORT).show()
        }

        //좀좀일기-삭제하기 버튼을 클릭한 경우
        delete_button.setOnClickListener {
            sqlDB=myHelper.writableDatabase
            //DB에서 해당 날짜의 일기 내용 삭제
            sqlDB.execSQL("DELETE FROM diaryTBL WHERE dateText='"+dateEditText.text.toString()+"';")
            sqlDB.close()
            Toast.makeText(this, "해당 좀좀일기가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
        }

        //좀좀일기-등록하기 버튼을 클릭한 경우
        plus_button.setOnClickListener {
            sqlDB = myHelper.writableDatabase
            //DB에서 해당 날짜에 일기 내용 추가
            sqlDB.execSQL(
                "INSERT INTO diaryTBL VALUES ('" + dateEditText.text.toString() + "','" +
                        diaryEditText.text.toString() + "');")
            sqlDB.close()
            Toast.makeText(this, "해당 좀좀일기가 등록되었습니다.", Toast.LENGTH_SHORT).show()
        }

//스피너
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var itemList = mutableListOf<String>("목표를 선택하세요")

        dbManager = DBManager(this, "groupTBL", null,1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        //목표 db가 null이 아니라면..
        cursor = sqlitedb.rawQuery("SELECT * FROM groupTBL", null)
        //cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null)

        while (cursor.moveToNext()){
            var str_Goal = cursor.getString(cursor.getColumnIndex("gGoaltext")).toString()


            itemList.add(str_Goal)

        }

        cursor.close()
        sqlitedb.close()

        ////////////////
        /*//받은 목표 집어넣기
        var str_Goal = cursor.getString(cursor.getColumnIndex("goal")).toString()

        var goalll: TextView = TextView(this)
        goalll.text = str_Goal
        itemList.add("goalll")*/
        /////////////////////////

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, itemList)
        spinner2 = findViewById(R.id.spinner2)
        spinner2.adapter = adapter


        spinner2.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position != 0)
                    Toast.makeText(this@MainActivity, itemList[position],
                        Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()

    }

    //날짜 화면 보여주기
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView(){
        binding.monthYearText.text = monthYearFromDate(CalendarUtill.selectedDate)

        val dayList = dayInMonthArray(CalendarUtill.selectedDate)

        val adapter = CalendarAdapter(dayList)

        var manager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)

        binding.recyclerView.layoutManager = manager

        binding.recyclerView.adapter = adapter
    }

    //날짜 타입 설정
    @RequiresApi(Build.VERSION_CODES.O)
    private fun monthYearFromDate(date: LocalDate):String{

        var formatter = DateTimeFormatter.ofPattern("MM월 yyyy")
        return date.format(formatter)
    }

    //날짜 생성
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

    inner class  myDBHelper(context: Context): SQLiteOpenHelper(context,"diaryDB",null,1){

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("Create TABLE diaryTBL (dateText CHAR PRIMARY KEY, diaryText CHAR);")

        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS diaryTBL")
            onCreate(db)
        }


    }
}