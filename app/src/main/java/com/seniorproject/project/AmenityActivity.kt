package com.seniorproject.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.recyclerview.widget.LinearLayoutManager
import com.seniorproject.project.Adapters.FeedAdapter
import com.seniorproject.project.Adapters.ListAdapter
import kotlinx.android.synthetic.main.activity_amenity.*
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.activity_event.eveList

class AmenityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amenity)
        supportActionBar?.show()
        supportActionBar?.setTitle(
                Html.fromHtml("<font color='#FFFFFF'>" +
                        "\t".repeat(17) +
                        "Amenity</font>"))
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL,false)
        amenList.layoutManager = linearLayoutManager
        var random= listOf<String>("Hello","World","This","is","test","list")
        val adapter =
                ListAdapter(
                        random,
                        baseContext
                )
        amenList.adapter=adapter
    }
}