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
    lateinit var goal_plus_button:View
    lateinit var edtGoal : EditText

    lateinit var sqlDB : SQLiteDatabase
    lateinit var myHelper : SettingGoal.myDBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goal_setting)
        setVisible(false)

        edtGoal = findViewById(R.id.goal_editText)
        goal_plus_button=findViewById(R.id.rectangle_7)
        myHelper = myDBHelper(this)
        val intent = Intent(this, MainActivity::class.java)
        val dateStr = intent.getStringExtra("intent_date").toString()

        goal_plus_button.setOnClickListener {
            sqlDB = myHelper.writableDatabase
            myHelper.onUpgrade(sqlDB, 1, 2)

            var str_Goal : String = edtGoal.text.toString()
            sqlDB.execSQL("INSERT INTO groupTBL VALUES ('str_Goal', 'dateStr')")
            sqlDB.close()

            Toast.makeText(this, "좀좀목표가 등록되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)}

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
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