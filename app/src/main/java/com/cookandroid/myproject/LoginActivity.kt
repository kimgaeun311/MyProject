package com.cookandroid.myproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar

class LoginActivity : AppCompatActivity() {
    lateinit var login_button:Button
    lateinit var register_button: TextView

    lateinit var edtID : EditText
    lateinit var edtPASSWORD : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setVisible(false)
        edtID = findViewById(R.id.edtID)
        edtPASSWORD = findViewById(R.id.editPASSWORD)


        title = "로그인"

        login_button=findViewById(R.id.login_button)

        login_button.setOnClickListener {


            Toast.makeText(this, edtID.text.toString()+ "님 어서오세요!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        register_button = findViewById(R.id.registerLogin)

        register_button.setOnClickListener {

            val intent = Intent(this, registerActivity::class.java)
            startActivity(intent)
        }

    }
}