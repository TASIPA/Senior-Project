package com.seniorproject.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.seniorproject.project.Adapters.AttractionAdapter
import com.seniorproject.project.Adapters.RailwayAdapter
import com.seniorproject.project.models.RailwayData
import com.seniorproject.project.models.Restaurants
import kotlinx.android.synthetic.main.activity_attraction.*
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_category.back_btn
import kotlinx.android.synthetic.main.activity_railway.RailList


class RailwayActivity : AppCompatActivity() {

    lateinit var traindata:MutableList<RailwayData>
    lateinit var db: FirebaseFirestore
    lateinit var adapter: RailwayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_railway)
        supportActionBar?.hide()

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)//back to the login page
            startActivity(intent)
        }

        back_btn.setOnClickListener {
            finish()
        }

        traindata= mutableListOf()
        db= FirebaseFirestore.getInstance()
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL,false)
        RailList.layoutManager = linearLayoutManager

        val docRef = db.collection("Train")
        docRef.get()//ordering ...
            .addOnSuccessListener { snapShot ->//this means if read is successful then this data will be loaded to snapshot
                if (snapShot != null) {
                    traindata!!.clear()
                    traindata = snapShot.toObjects(RailwayData::class.java)
                    adapter = RailwayAdapter(traindata, baseContext)
                    RailList.adapter=adapter
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

//        var data= listOf<RailwayData>(RailwayData("356","Ordinary","Salaya","07:00 am","Bangkok","08:05 am"),
//            RailwayData("84","Special Express","Salaya","07:34 am","Bangkok","08:20 am"),
//            RailwayData("172","Rapid","Salaya","08:14 am","Bangkok","08:50 am"),
//            RailwayData("38","Special Express","Salaya","09:10 am","Bangkok","10:10 am"),
//            RailwayData("261","Ordinary","Salaya","10:15 am","Hua Hin","01:35 pm"),
//            RailwayData("251","Ordinary","Salaya","01:33 pm","Phrachuchap Khiri Khan","05:20 pm")
//        )
        val adapter = RailwayAdapter(traindata,baseContext)

        RailList.adapter = adapter
    }
}