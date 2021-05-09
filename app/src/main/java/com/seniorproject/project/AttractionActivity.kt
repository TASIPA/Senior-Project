package com.seniorproject.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import com.seniorproject.project.Adapters.AttractionAdapter
import com.seniorproject.project.Interface.onItemClickListener
import com.seniorproject.project.models.Attraction
import kotlinx.android.synthetic.main.activity_attraction.*


class AttractionActivity : AppCompatActivity(),onItemClickListener {
    lateinit var attmock:List<Attraction>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attraction)
        supportActionBar?.hide()
        back_btn.setOnClickListener {
            finish()
        }
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL,false)
        attList.layoutManager = linearLayoutManager
        //replace with event adapter
        attmock= listOf<Attraction>(Attraction("Lumphini Park","attpic1","Park","Lumphini, Bangkok",0.0),
            Attraction("Grand Palace","attpic2","Sightseeing","Dusit, Bangkok",0.0),
            Attraction("Bang KraJao","attpic3","Park","Phra Pradaeng, Samut Prakan",0.0),
            Attraction("Erawan National Park","attpic4","Sightseeing","Tha Kradan, Kanjanaburi",0.0),
            Attraction("Mahidol University","attpic5","Educational institute","Salaya, Nakhon Pathom",0.0))
        val adapter = AttractionAdapter(attmock, baseContext,this)
        attList.adapter=adapter
    }

    override fun onItemClick(position: Int) {
        //change to attractiondetailpage too
        var intent= Intent(this,EveDetailActivity::class.java)
        intent.putExtra("name",attmock[position].name)
        intent.putExtra("type",attmock[position].category)
        intent.putExtra("loc",attmock[position].location)
        intent.putExtra("image",attmock[position].pic)
        //intent.putExtra("rating",res[position].rating.toString())
        startActivity(intent)

    }
}
