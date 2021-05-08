package com.seniorproject.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.seniorproject.project.Adapters.RailwayAdapter
import com.seniorproject.project.models.RailwayData
import kotlinx.android.synthetic.main.activity_railway.RailList
import kotlinx.android.synthetic.main.activity_railway.webBTN


class RailwayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_railway)

        webBTN.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)//back to the login page
            startActivity(intent)
        }
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL,false)
        RailList.layoutManager = linearLayoutManager
        var data= listOf<RailwayData>(RailwayData("356","Ordinary","Salaya","07:00 am","Bangkok","08:05 am"),
            RailwayData("84","Special Express","Salaya","07:34 am","Bangkok","08:20 am"),
            RailwayData("172","Rapid","Salaya","08:14 am","Bangkok","08:50 am"),
            RailwayData("38","Special Express","Salaya","09:10 am","Bangkok","10:10 am"),
            RailwayData("261","Ordinary","Salaya","10:15 am","Hua Hin","01:35 pm"),
            RailwayData("251","Ordinary","Salaya","01:33 pm","Phrachuchap Khiri Khan","05:20 pm")
        )
        val adapter = RailwayAdapter(data,baseContext)

        RailList.adapter = adapter
    }
}