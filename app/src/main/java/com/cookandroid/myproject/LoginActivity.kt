package com.cookandroid.myproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.cookandroid.myproject.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binging : ActivityMainBinding
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



        binging = ActivityMainBinding.inflate(layoutInflater)

        var dbHelper: registerActivity.registerDB = registerActivity.registerDB(applicationContext,"registerTBL.db", null, 1)



        title = "로그인"

        login_button=findViewById(R.id.login_button)

        //로그인 성공 할때와 그렇지 않을 때
        login_button.setOnClickListener {


            if(dbHelper.getResult1(edtID.getText().toString(), edtPASSWORD.getText().toString())==true){

                Toast.makeText(this, edtID.text.toString()+ "님 어서오세요!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }

            else{
                Toast.makeText(this, "정보가 잘못 입력되었습니다", Toast.LENGTH_SHORT).show()
            }

        }

        register_button = findViewById(R.id.registerLogin)

        //회원가입 화면 넘어감
        register_button.setOnClickListener {

            val intent = Intent(this, registerActivity::class.java)
            startActivity(intent)
        }

    }
}