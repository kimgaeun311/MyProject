package com.cookandroid.myproject
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class SettingGoal : AppCompatActivity() {
    lateinit var goal_plus_button:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goal_setting)
        setVisible(false)

        goal_plus_button=findViewById(R.id.rectangle_7)
        goal_plus_button.setOnClickListener {
            Toast.makeText(this, "좀좀목표가 등록되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)}


    }
}