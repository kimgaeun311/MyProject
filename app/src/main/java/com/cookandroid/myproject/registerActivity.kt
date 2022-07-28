package com.cookandroid.myproject

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class registerActivity : AppCompatActivity() {

    lateinit var userName: EditText
    lateinit var userID: EditText
    lateinit var userPassword : EditText

    lateinit var entBtn:Button

    lateinit var reHelper:registerDB
    lateinit var sqlDB: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        userName = findViewById(R.id.nameTextEdit)
        userID = findViewById(R.id.idTextEdit)
        userPassword = findViewById(R.id.passwordTextEdit)

        entBtn =findViewById(R.id.Enterbutton)

        reHelper = registerDB(this)

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

    inner class registerDB(context: Context) : SQLiteOpenHelper(context,"register", null,1){

        override fun onCreate(db: SQLiteDatabase?) {

            db!!.execSQL("CREATE TABLE registerTBL (uName CHAR(20), uID Integer PRIMARY KEY, uPASSWORD Integer);")

        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

            db!!.execSQL("DROP TABLE IF EXISTS registerTBL")
            onCreate(db)

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