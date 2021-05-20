package com.seniorproject.project

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seniorproject.project.EmergencyService.EmergencyActivity

import kotlinx.android.synthetic.main.activity_category.*

class Allcategories : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportActionBar!!.hide()
        back_btn.setOnClickListener {
            finish()
        }
        cat_res.setOnClickListener {
            var intent= Intent(this,RestaurantActivity::class.java)
            startActivity(intent)

        }
        cat_att.setOnClickListener {
            var intent= Intent(this,AttractionActivity::class.java)
            startActivity(intent)
        }
        cat_eve.setOnClickListener {
            var intent= Intent(this,EventActivity::class.java)
            startActivity(intent)

        }
        cat_ame.setOnClickListener {
            var intent= Intent(this,AmenityActivity::class.java)
            startActivity(intent)

        }
        cat_train.setOnClickListener {
            var intent= Intent(this,RailwayActivity::class.java)
            startActivity(intent)

        }
        cat_rep.setOnClickListener {
            var intent= Intent(this,ReportActivity::class.java)
            startActivity(intent)

        }
        qr.setOnClickListener {
            var intent= Intent(this,QrcodeActivity::class.java)
            startActivity(intent)
            finish()
        }
        cat_pro.setOnClickListener {
            var intent= Intent(this,PromtionActivity::class.java)
            startActivity(intent)

        }
        cat_eme.setOnClickListener {
            var intent= Intent(this,EmergencyActivity::class.java)
            startActivity(intent)
        }
    }}