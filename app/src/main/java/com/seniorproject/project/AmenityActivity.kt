package com.seniorproject.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.seniorproject.project.Adapters.AmenityAdapter
import com.seniorproject.project.Interface.onItemClickListener
import com.seniorproject.project.models.Restaurants
import kotlinx.android.synthetic.main.activity_amenity.*
import kotlinx.android.synthetic.main.activity_amenity.back_btn
import kotlinx.android.synthetic.main.activity_restaurant.*


class AmenityActivity : AppCompatActivity(),onItemClickListener {
    lateinit var amedata:MutableList<Restaurants>
    lateinit var db: FirebaseFirestore
    lateinit var adapter: AmenityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amenity)
       supportActionBar?.hide()
        back_btn.setOnClickListener {
            finish()
        }
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL,false)
        amenList.layoutManager = linearLayoutManager
        amedata= mutableListOf()
        db= FirebaseFirestore.getInstance()

        val docRef = db.collection("Amenities")
        docRef.get()//ordering ...
            .addOnSuccessListener { snapShot ->//this means if read is successful then this data will be loaded to snapshot
                if (snapShot != null) {
                    amedata!!.clear()
                    amedata = snapShot.toObjects(Restaurants::class.java)
                    adapter = AmenityAdapter(amedata, baseContext,this)
                    amenList.adapter=adapter
                }

            }//in case it fails, it will toast failed
            .addOnFailureListener { exception ->
                Log.d(
                    "FirebaseError",
                    "Fail:",
                    exception
                )//this is kind a debugger to check whether working correctly or not
                Toast.makeText(baseContext,"Fail to read database", Toast.LENGTH_SHORT).show()

            }
    }

    override fun onItemClick(position: Int) {
        var intent= Intent(this,AmeDetailActivity::class.java)
        intent.putExtra("ameObj",amedata[position])
        startActivity(intent)

    }
}