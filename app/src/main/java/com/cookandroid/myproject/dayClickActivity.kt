package com.cookandroid.myproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.cookandroid.myproject.databinding.ActivityMainBinding

class dayClickActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var spinner1: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        val view = binding.root
        //setContentView(spinner1)
        setContentView(R.layout.activity_click_day)
        //setContentView(spinner1)

       supportActionBar?.setDisplayHomeAsUpEnabled(true)



        //arrayOf().arrayitems.toString()

        val itemList = listOf("목표를 선택하세요")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, itemList)
        spinner1 = findViewById(R.id.spinner)
        spinner1.adapter = adapter


        spinner1.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position != 0)
                    Toast.makeText(this@dayClickActivity, itemList[position],
                    Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
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