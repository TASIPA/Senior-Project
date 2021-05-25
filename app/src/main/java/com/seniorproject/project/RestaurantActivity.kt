package com.seniorproject.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

import com.seniorproject.project.Adapters.RestaurantAdapter
import com.seniorproject.project.Interface.onItemClickListener

import com.seniorproject.project.models.Restaurants
import kotlinx.android.synthetic.main.activity_restaurant.*
import java.util.*


class RestaurantActivity : AppCompatActivity(),onItemClickListener {
    lateinit var resdata: MutableList<Restaurants>
   // private lateinit var mDatabase:DatabaseReference
   lateinit var db:FirebaseFirestore
   lateinit var adapter:RestaurantAdapter
   var flag=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
        supportActionBar?.hide()
        back_btn.setOnClickListener {
            finish()
        }
        resdata= mutableListOf()
        db= FirebaseFirestore.getInstance()
       // mDatabase = FirebaseDatabase.getInstance().reference;
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL,false)
        resList.layoutManager = linearLayoutManager
        val docRef = db.collection("Restaurants")
        docRef.get()//ordering ...
            .addOnSuccessListener { snapShot ->//this means if read is successful then this data will be loaded to snapshot
                if (snapShot != null) {
                    resdata!!.clear()
                    resdata = snapShot.toObjects(Restaurants::class.java)
                    adapter = RestaurantAdapter(resdata!!, baseContext,this)
                    resList.adapter=adapter
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

         search_button.setOnClickListener {
             if (flag){
                 search_view.visibility= View.VISIBLE
                 flag=false
             }
             else{
                 search_view.visibility= View.GONE
                 flag=true
             }

         }
        res_search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                adapter.getFilter().filter(s)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        }

    override fun onItemClick(position: Int) {
        var intent= Intent(this,ResDetailActivity::class.java)
        intent.putExtra("Obj",resdata[position])

        startActivity(intent)
    }


}
