package com.seniorproject.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.recyclerview.widget.LinearLayoutManager
import com.seniorproject.project.Adapters.EventAdapter
import com.seniorproject.project.Adapters.ResAdapter
import com.seniorproject.project.models.Events
import com.seniorproject.project.models.Restaurants
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.activity_restaurant.*

class EventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        supportActionBar?.show()
        supportActionBar?.setTitle(
                Html.fromHtml("<font color='#FFFFFF'>" +
                        "\t".repeat(20) +
                        "Events</font>"))
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL,false)
        eveList.layoutManager = linearLayoutManager
        //replace with event adapter
        var eventmock= listOf<Events>(Events("Central Plaza Salaya SALE","epic1","sale","Central Plaza Salaya","23 March 2021",0.0),
                Events("MUIC X-Mas","epic2","college fair","Mahidol University","25 December 2021",0.0),
                Events("Makro SALE","epic3","sale","Makro Salaya","17 March 2021",0.0),
                Events("Motor Expo","epic4","exhibition","Impact Challenger Hall","23 October 2021",0.0),
                Events("Bangkok Furniture Fair","epic5","exhibition","Bitec Bangna","28 April 2021",0.0))
        val adapter = EventAdapter(eventmock, baseContext)
        eveList.adapter=adapter
    }
    }
