package com.seniorproject.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.seniorproject.project.Adapters.ResAdapter
import com.seniorproject.project.Interface.onItemClickListener
import com.seniorproject.project.models.Restaurants
import kotlinx.android.synthetic.main.activity_restaurant.*


class RestaurantActivity : AppCompatActivity(),onItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
        supportActionBar?.show()
        supportActionBar?.setTitle(
                Html.fromHtml("<font color='#FFFFFF'>" +
                        "\t".repeat(17) +
                        "Restaurants</font>"))
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL,false)
        resList.layoutManager = linearLayoutManager
        var res= listOf<Restaurants>(Restaurants("Kope Hya Tai Kee","pic7","Restaurant",4.5),
                Restaurants("Beef 35","pic2","Restaurant",4.0),
                Restaurants("Shindo Ramen","pic1","Restaurant",5.0),
                Restaurants("O Kra Joo NimCity","pic10","Restaurant",4.0),
                Restaurants("Yoi-Tenki Shabu","pic6","Restaurant",3.0))
        val adapter = ResAdapter(res, baseContext,this)
        resList.adapter=adapter
        }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position Clicked", Toast.LENGTH_SHORT).show()
    }


}
