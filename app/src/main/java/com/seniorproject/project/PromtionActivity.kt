package com.seniorproject.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.seniorproject.project.Adapters.PromoAdapter
import com.seniorproject.project.Adapters.RailwayAdapter
import com.seniorproject.project.models.Promotions
import com.seniorproject.project.models.RailwayData
import kotlinx.android.synthetic.main.activity_promtion.*
import kotlinx.android.synthetic.main.activity_railway.*
import kotlinx.android.synthetic.main.activity_railway.RailList

class PromtionActivity : AppCompatActivity() {

    lateinit var promodata:MutableList<Promotions>
    lateinit var db: FirebaseFirestore
    lateinit var adapter: PromoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promtion)
        supportActionBar?.hide()

        promodata= mutableListOf()
        db= FirebaseFirestore.getInstance()
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL,false)
        promoList.layoutManager = linearLayoutManager

        val docRef = db.collection("Promotion")
        docRef.get()//ordering ...
            .addOnSuccessListener { snapShot ->//this means if read is successful then this data will be loaded to snapshot
                if (snapShot != null) {
                    promodata!!.clear()
                    promodata = snapShot.toObjects(Promotions::class.java)
                    adapter = PromoAdapter(promodata, baseContext)
                    promoList.adapter=adapter
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

        val adapter = PromoAdapter(promodata,baseContext)

        promoList.adapter = adapter

    }
}