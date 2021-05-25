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
import com.google.firebase.firestore.FirebaseFirestore
import com.seniorproject.project.Adapters.EventAdapter
import com.seniorproject.project.Adapters.RestaurantAdapter
import com.seniorproject.project.Interface.onItemClickListener
import com.seniorproject.project.Interface.onItemClickListener1
import com.seniorproject.project.models.Events
import com.seniorproject.project.models.Restaurants
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.activity_event.back_btn
import kotlinx.android.synthetic.main.activity_event.search_button
import kotlinx.android.synthetic.main.activity_event.search_view
import kotlinx.android.synthetic.main.activity_restaurant.*


class EventActivity : AppCompatActivity(), onItemClickListener1 {
    lateinit var evedata:MutableList<Events>
    var flag=true
    lateinit var db: FirebaseFirestore
    lateinit var adapter:EventAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        supportActionBar?.hide()
        back_btn.setOnClickListener {
            finish()
        }
        db= FirebaseFirestore.getInstance()
        evedata= mutableListOf()
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL,false)
        eveList.layoutManager = linearLayoutManager
        //replace with event adapter
        val docRef = db.collection("Events")
        docRef.get()//ordering ...
            .addOnSuccessListener { snapShot ->//this means if read is successful then this data will be loaded to snapshot
                if (snapShot != null) {
                    evedata.clear()
                    evedata = snapShot.toObjects(Events::class.java)
                    adapter = EventAdapter(evedata, baseContext,this)
                    eveList.adapter=adapter
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
        eve_search.addTextChangedListener(object : TextWatcher {
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

    override fun onItemClick(position: Int, data: MutableList<Events>) {
        var intent= Intent(this,EveDetailActivity::class.java)
        intent.putExtra("name",evedata[position].Name)
        intent.putExtra("type",evedata[position].Category)
        intent.putExtra("image",evedata[position].imageURL)
        //intent.putExtra("rating",res[position].rating.toString())
        startActivity(intent)
    }
}
