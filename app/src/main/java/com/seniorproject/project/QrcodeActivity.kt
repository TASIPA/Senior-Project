package com.seniorproject.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_qrcode.*

class QrcodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)
        supportActionBar!!.hide()
        AttPic.setImageResource(R.drawable.attpic1)
    }
}