package com.seniorproject.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_railway.*

class Railway : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_railway)

        webBTN.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)//back to the login page
            startActivity(intent)
            finish()
        }
    }
}