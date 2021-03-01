package com.seniorproject.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.recyclerview.widget.LinearLayoutManager
import com.seniorproject.project.Adapters.ResAdapter
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        supportActionBar?.show()
        supportActionBar?.setTitle(
                Html.fromHtml("<font color='#FFFFFF'>" +
                        "\t".repeat(17) +
                        "Events</font>"))
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL,false)
        eveList.layoutManager = linearLayoutManager
        var random= listOf<String>("Hello","World","This","is","test","list")
        val adapter =
                ResAdapter(
                        random,
                        baseContext
                )
        eveList.adapter=adapter
    }
    }
