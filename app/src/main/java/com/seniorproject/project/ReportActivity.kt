package com.seniorproject.project

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_report.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm ")
        val formatted = current.format(formatter)

        dateShowReport.text = "Date: $formatted"
    }
}