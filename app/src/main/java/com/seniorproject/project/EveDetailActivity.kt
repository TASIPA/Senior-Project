package com.seniorproject.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.seniorproject.project.models.Events
import com.seniorproject.project.models.Restaurants


import kotlinx.android.synthetic.main.activity_eve_detail.*

class EveDetailActivity : AppCompatActivity()/*, OnMapReadyCallback*/ {
    lateinit var obj: Events
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eve_detail)
        supportActionBar?.hide()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
       obj= intent.getSerializableExtra("eveObj") as Events


        eve_name.text = obj.Name
        //EveType.text = type
        var result=when (obj.imageURL) {
            "epic1" -> R.drawable.epic1
            "epic2" -> R.drawable.epic2
            "epic3"-> R.drawable.epic3
            "epic4"-> R.drawable.epic4
            else -> R.drawable.epic5
        }
        eve_desc.text=obj.Description
        eve_loc.text=obj.Location
        eve_type.text=obj.Category
        eve_pic.setImageResource(result)

    }

    fun onClick(v: View) {
        eve_detailLayout.visibility = View.GONE
        eve_reviewLayout.visibility = View.GONE
        eve_button3.setBackgroundResource(R.color.white)
        eve_button2.setBackgroundResource(R.color.white)

        when (v.id) {
            R.id.eve_button2 -> {
                eve_detailLayout.visibility = View.VISIBLE
                eve_button2.setBackgroundResource(R.color.secondary)
            }

            R.id.eve_button3 -> {
                eve_reviewLayout.visibility = View.VISIBLE
                eve_button3.setBackgroundResource(R.color.secondary)
            }

        }

    }
}