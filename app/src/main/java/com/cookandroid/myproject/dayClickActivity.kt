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


       // var items = arrayOf("1","2","3","4")
        var arrayitems = ArrayList<String>() // 불러올 때 0 ~ 배열크기만큼 불러오기
        arrayitems.add("목표를 선택하세요")
        arrayitems.add("잠자기")

        //arrayOf().arrayitems.toString()

        val itemList = listOf("목표를 선택하세요", "잠자기")

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


        //스페너 전
        /*
        ArrayAdapter.createFromResource(
            this,
            arrayitems.get(),
            R.layout.spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner1.adapter = adapter
        }
        */


        /*
        * ArrayAdapter.createFromResource(
            this,
            R.array.my_list,
            R.layout.spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner1.adapter = adapter
        }
        * */
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