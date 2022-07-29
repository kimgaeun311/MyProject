package com.cookandroid.myproject
import android.content.Context
import android.database.Cursor
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    lateinit var sqlDB: SQLiteDatabase


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "좀좀일기"

        //스페너 시작
        //val spinner2 = findViewById<Spinner>(R.id.spinner2)
        //스페너 끝

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



        goal_plus.setOnClickListener {
            val intent = Intent(this, SettingGoal::class.java)
            startActivity(intent)
        }

        diary_search.setOnClickListener {
            sqlDB = myHelper.readableDatabase
            var cursor: Cursor
            cursor = sqlDB.rawQuery("SELECT * FROM diaryTBL WHERE dateText='"+dateEditText.text.toString()+"';", null)
            var diary_toast:String
            if (cursor.moveToNext()){
                diary_toast=cursor.getString(1)
                diaryEditText.setText(diary_toast)
                Toast.makeText(this, "해당 날짜의 일기 조회 완료", Toast.LENGTH_SHORT).show()
            }
            else{
                diaryEditText.setText("(해당 날짜의 일기 없음)")
            }
            cursor.close()
            sqlDB.close()
        }

        modify_button.setOnClickListener {
            sqlDB=myHelper.writableDatabase

            sqlDB.execSQL("UPDATE diaryTBL SET diaryText= '"+diaryEditText.text+"' WHERE dateText = '"+
                    dateEditText.text.toString()+"';")

            sqlDB.close()

            Toast.makeText(this, "해당 좀좀일기가 수정되었습니다.", Toast.LENGTH_SHORT).show()
        }

        delete_button.setOnClickListener {
            sqlDB=myHelper.writableDatabase

            sqlDB.execSQL("DELETE FROM diaryTBL WHERE dateText='"+dateEditText.text.toString()+"';")
            sqlDB.close()
            Toast.makeText(this, "해당 좀좀일기가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
        }

        plus_button.setOnClickListener {
            sqlDB = myHelper.writableDatabase

            sqlDB.execSQL(
                "INSERT INTO diaryTBL VALUES ('" + dateEditText.text.toString() + "','" +
                        diaryEditText.text.toString() + "');")

            sqlDB.close()

            Toast.makeText(this, "해당 좀좀일기가 등록되었습니다.", Toast.LENGTH_SHORT).show()
        }

//스피너
        val spinner2: Spinner = findViewById(R.id.spinner2)

        ArrayAdapter.createFromResource(
            this,
            R.array.my_list,
            R.layout.spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner2.adapter = adapter
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