package com.cookandroid.myproject
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast

class SettingGoal : AppCompatActivity() {
    lateinit var goal_plus_button: View
    lateinit var edtGoal: EditText

    lateinit var sqlDB: SQLiteDatabase
    lateinit var myHelper: SettingGoal.myDBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goal_setting)
        setVisible(false)

        // 위젯 연결
        edtGoal = findViewById(R.id.goal_editText)      // 목표 입력받을 텍스트
        goal_plus_button = findViewById(R.id.rectangle_7)       // 등록하기 버튼
        myHelper = myDBHelper(this)         // DBHelper


        val intent = Intent(this, MainActivity::class.java)         // main->settinggoal intent
        val dateStr = intent.getStringExtra("intent_date").toString()       // 추후 선택날짜 받아올때 대비해 만든 인텐트

        // DB에 목표 내용 저장하기
        goal_plus_button.setOnClickListener {
            sqlDB = myHelper.writableDatabase
            myHelper.onUpgrade(sqlDB, 1, 2)     // 버전 업데이트

            var str_Goal: String = edtGoal.text.toString()
            sqlDB.execSQL("INSERT INTO groupTBL VALUES ('str_Goal', 'dateStr')")
            sqlDB.close()       // 닫기 필수(메모리 누수 예방)

            // 등록 이후 토스트 출력 및 MainActivity로 넘어가기
            Toast.makeText(this, "좀좀목표가 등록되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)   // 넘기기
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }


    // 옵션 메뉴 구현 (스피너 관련)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    // DB관련 클래스 (onCreate와 onUpgrade)
    inner class myDBHelper(context: Context) : SQLiteOpenHelper(context, "goalDB", null, 1) {
        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL("CREATE TABLE groupTBL (gGoal text, gDate text);")
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("DROP TABLE IF EXISTS groupTBL")
            onCreate(p0)
        }

    }
}
