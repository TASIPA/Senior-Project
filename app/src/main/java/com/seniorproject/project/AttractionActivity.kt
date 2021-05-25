package com.seniorproject.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.seniorproject.project.Adapters.AttractionAdapter
import com.seniorproject.project.Interface.onItemClickListener
import com.seniorproject.project.models.Restaurants
import kotlinx.android.synthetic.main.activity_attraction.*
import kotlinx.android.synthetic.main.activity_attraction.back_btn
import kotlinx.android.synthetic.main.activity_restaurant.*


class AttractionActivity : AppCompatActivity(),onItemClickListener {
    lateinit var attdata:MutableList<Restaurants>
    lateinit var db: FirebaseFirestore
    lateinit var adapter: AttractionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attraction)
        supportActionBar?.hide()
        back_btn.setOnClickListener {
            finish()
        }
        attdata= mutableListOf()
        db= FirebaseFirestore.getInstance()
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL,false)
        attList.layoutManager = linearLayoutManager
        //replace with event adapter
        val docRef = db.collection("Attractions")
        docRef.get()//ordering ...
            .addOnSuccessListener { snapShot ->//this means if read is successful then this data will be loaded to snapshot
                if (snapShot != null) {
                    attdata!!.clear()
                    attdata = snapShot.toObjects(Restaurants::class.java)
                    adapter = AttractionAdapter(attdata, baseContext,this)
                    attList.adapter=adapter
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
        var intent= Intent(this,AttDetailActivity::class.java)
        intent.putExtra("attObj",attdata[position])
        startActivity(intent)

    }
}
