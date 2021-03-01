package com.seniorproject.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.recyclerview.widget.LinearLayoutManager
import com.seniorproject.project.Adapters.AmenityAdapter
import com.seniorproject.project.Adapters.EventAdapter
import com.seniorproject.project.Adapters.ResAdapter
import com.seniorproject.project.models.Amenities
import com.seniorproject.project.models.Events
import kotlinx.android.synthetic.main.activity_amenity.*
import kotlinx.android.synthetic.main.activity_event.*

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
        var amenmock= listOf<Amenities>(Amenities("PTT Gas Station","apic1","Gas",0.0),
                Amenities("Kasikorn Bank","apic2","Bank",0.0),
                Amenities("KrungThai Bank","apic3","Bank",0.0),
                Amenities("Bangkok Bank","apic4","Bank",0.0),
                Amenities("7-11 The september branch","apic5","Convenient Store",0.0))
        val adapter = AmenityAdapter(amenmock, baseContext)
        amenList.adapter=adapter
    }
}