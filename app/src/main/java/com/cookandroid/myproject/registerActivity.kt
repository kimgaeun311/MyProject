package com.cookandroid.myproject

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class registerActivity : AppCompatActivity() {

    lateinit var userName: EditText
    lateinit var userID: EditText
    lateinit var userPassword : EditText

    lateinit var entBtn:Button

    lateinit var reHelper:registerDB
    lateinit var sqlDB: SQLiteDatabase


    //회원가입 DB 테이블 생성
    @Suppress("DEPRECATION")
    class registerDB(context: Context, s: String, nothing: Nothing?, i: Int) : SQLiteOpenHelper(context,"register", null,1){

        override fun onCreate(db: SQLiteDatabase?) {

            db!!.execSQL("CREATE TABLE registerTBL (uName CHAR(20), uID Integer PRIMARY KEY, uPASSWORD Integer);")

        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

            db!!.execSQL("DROP TABLE IF EXISTS registerTBL")
            onCreate(db)

        }

        //받아온 DB 값과 입력한 값을 비교하기 위한 함수
        fun getResult(): String {
            var db: SQLiteDatabase = readableDatabase
            var result: String = ""

            var cursor: Cursor = db.rawQuery("SELECT * FROM registerTBL", null)
            while (cursor.moveToNext()) {
                result += (cursor.getString(0)
                        + " : "
                        + cursor.getString(1)
                        + " : "
                        + cursor.getString(2)
                        + " : "
                        + cursor.getString(3)
                        + " : "
                        + cursor.getString(4)
                        + " : "
                        + cursor.getString(5)
                        + " : "
                        + cursor.getString(6)
                        + " : "
                        + cursor.getString(7)
                        + "\n")

            }

            return result
        }

        //입력된 아이디, 패스워드와 저장된 정보들이 일치 또는 그렇지 않을 때의 조건문
        fun getResult1(uID: String, uPASSWORD: String): Boolean {
            var db: SQLiteDatabase = readableDatabase
            var result: String = ""

            var cursor: Cursor = db.rawQuery("SELECT uID, uPASSWORD FROM registerTBL", null)
            while (cursor.moveToNext()) {
                result = (cursor.getString(0))
                if (result.equals(uID)) {
                    if (cursor.getString(1).equals(uPASSWORD)) {
                        return true
                        break
                    } else {
                        return false
                    }
                }else {

                }
            }

            return false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        userName = findViewById(R.id.nameTextEdit)
        userID = findViewById(R.id.idTextEdit)
        userPassword = findViewById(R.id.passwordTextEdit)

        entBtn =findViewById(R.id.Enterbutton)

        reHelper = registerDB(this, "registerTBL", null, 1)

        entBtn.setOnClickListener {



            sqlDB = reHelper.writableDatabase

            sqlDB.execSQL("INSERT INTO registerTBL VALUES ('"+userName.text.toString()+"',"
                    +userID.text.toString()+ ","+userPassword.text.toString()+");")

            userID.getText().toString()
            userPassword.getText().toString()

            sqlDB.close()


                Toast.makeText(applicationContext,"등록되었습니다", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)



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
    }
}