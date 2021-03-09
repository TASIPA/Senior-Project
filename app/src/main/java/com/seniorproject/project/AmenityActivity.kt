package com.seniorproject.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.recyclerview.widget.LinearLayoutManager
import com.seniorproject.project.Adapters.AmenityAdapter
import com.seniorproject.project.Interface.onItemClickListener
import com.seniorproject.project.models.Amenities
import kotlinx.android.synthetic.main.activity_amenity.*

class AmenityActivity : AppCompatActivity(),onItemClickListener {
    lateinit var amenmock:List<Amenities>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amenity)
        supportActionBar?.show()
        supportActionBar?.setTitle(
                Html.fromHtml("<font color='#FFFFFF'>" +
                        "\t".repeat(18) +
                        "Amenity</font>"))
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL,false)
        amenList.layoutManager = linearLayoutManager
        amenmock= listOf<Amenities>(Amenities("PTT Gas Station","apic1","Gas",0.0),
                Amenities("Kasikorn Bank","apic2","Bank",0.0),
                Amenities("KrungThai Bank","apic3","Bank",0.0),
                Amenities("Bangkok Bank","apic4","Bank",0.0),
                Amenities("7-11 The september branch","apic5","Convenient Store",0.0))
        val adapter = AmenityAdapter(amenmock, baseContext,this)
        amenList.adapter=adapter
    }

    override fun onItemClick(position: Int) {
        var intent= Intent(this,AmeDetailActivity::class.java)
        intent.putExtra("name",amenmock[position].name)
        intent.putExtra("type",amenmock[position].type)
        intent.putExtra("image",amenmock[position].pic)
        //intent.putExtra("rating",amenmock[position].rating.toString())
        startActivity(intent)

    }
}