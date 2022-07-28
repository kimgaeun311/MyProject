package com.cookandroid.myproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.cookandroid.myproject.R.array.my_list
import com.cookandroid.myproject.databinding.ActivityMainBinding
import java.text.DateFormatSymbols

class dayClickActivity : AppCompatActivity() {


    //val list:Array<String> = resources.getStringArray(R.array.my_list)
    //var list = resources.getStringArray(R.array.my_list) //오류남...
    lateinit var spinner_list : Spinner

    var lists = DateFormatSymbols().months

    lateinit var binding : ActivityMainBinding // 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_click_day)

        binding = ActivityMainBinding.inflate(layoutInflater) //1

        val view = binding.root //1
        setContentView(view)//1
        val itemList = listOf("성별을 선택하세요", "남자", "여자")//1
        val adapter = ArrayAdapter(this, R.layout.activity_click_day, itemList)//1
        binding.spinner.adapter = adapter//1

        binding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{//1
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {//1
                if(position != 0)//1
                    Toast.makeText(this@dayClickActivity, itemList[position], Toast.LENGTH_SHORT).show()//1
            }//1
            override fun onNothingSelected(parent: AdapterView<*>?) {//1

            }//1
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //String[] strList = getResources().getStringArray(R.array.my_list)
        //사용은....


        //spinnerAdapter()
        /*
        spinner_list = findViewById(R.id.spinner_list)
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, my_list)
        spinner_list.adapter = adapter

        spinner_list.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        */


    }

/*
    fun spinnerAdapter(){
        spinner_list = findViewById(R.id.spinner_list)
        var adapter = ArrayAdapter<String>(this, android.R.layout.activity_list_item, my_list)
        spinner_list.adapter = adapter

        spinner_list.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

    }*/

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