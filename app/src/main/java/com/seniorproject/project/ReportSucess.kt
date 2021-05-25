package com.seniorproject.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.seniorproject.project.ui.Home.HomeFragment
import com.seniorproject.project.ui.Home.HomeViewModel
import kotlinx.android.synthetic.main.activity_report_sucess.*

class ReportSucess : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_sucess)
        supportActionBar?.hide()
        val ID = intent.getStringExtra("ReportID").toString()
        ReportID.text = ID

        HomeBtn.setOnClickListener {
            finish()
//            val intent = Intent(this,HomeViewModel::class.java)
//            startActivity(intent)
        }
    }
}