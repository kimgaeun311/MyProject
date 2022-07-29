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
        //아이디&패스워드 연결
        edtID = findViewById(R.id.edtID)
        edtPASSWORD = findViewById(R.id.editPASSWORD)

        binging = ActivityMainBinding.inflate(layoutInflater)

        //아이디&패스워드 DB
        var dbHelper: registerActivity.registerDB = registerActivity.registerDB(applicationContext,"registerTBL.db", null, 1)

        title = "로그인"

        login_button=findViewById(R.id.login_button)

        //로그인 버튼 클릭
        login_button.setOnClickListener {
            //아이디&패스워드 값 가져오기
            if(dbHelper.getResult1(edtID.getText().toString(), edtPASSWORD.getText().toString())==true){

                //로그인 성공 시 아이디와 함께 토스트창 출력 및 메인 화면으로 이동
                Toast.makeText(this, edtID.text.toString()+ "님 어서오세요!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }

            else{
                //잘못된 로그인 정보를 입력한 경우
                Toast.makeText(this, "정보가 잘못 입력되었습니다", Toast.LENGTH_SHORT).show()
            }

        }
        //아이디가 없는 경우 회원가입 -> 회원가입 버튼 클릭 시 가입 화면으로 이동
        register_button = findViewById(R.id.registerLogin)

        register_button.setOnClickListener {

            val intent = Intent(this, registerActivity::class.java)
            startActivity(intent)
        }

    }
}