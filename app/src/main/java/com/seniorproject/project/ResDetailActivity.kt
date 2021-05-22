package com.seniorproject.project

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.*
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.seniorproject.project.models.Favorite
import com.seniorproject.project.models.Restaurants

import kotlinx.android.synthetic.main.activity_res_detail.*

class ResDetailActivity : AppCompatActivity(), OnMapReadyCallback, ValueEventListener {

    private lateinit var mMap: GoogleMap
    private lateinit var latLng: LatLng

    var rootNode: FirebaseDatabase? = null
    var reference: DatabaseReference? = null
    var auth: FirebaseAuth? = null
    var checked: Boolean=false
    lateinit var obj:Restaurants

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_res_detail)
        supportActionBar?.hide()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.res_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        //firebase hooks
        auth = FirebaseAuth.getInstance()
        var currentuser = auth!!.currentUser!!.uid
        rootNode = FirebaseDatabase.getInstance()
        reference = rootNode!!.getReference("favorite").child(currentuser)
        //get value
        obj= intent.getSerializableExtra("Obj") as Restaurants
        //calls onDataChanged()
        reference!!.child(obj.imageURL).addListenerForSingleValueEvent(this)
        res_favBtn.setOnClickListener {
            if (!checked) {
                res_favBtn.setColorFilter(
                    ContextCompat.getColor(baseContext, R.color.red), PorterDuff.Mode.SRC_IN
                )
                reference!!.child(obj.imageURL).setValue(
                    Favorite(
                        obj.Name,
                        obj.imageURL,
                        obj.Category,
                        obj.Rating,
                       obj.distance,
                        id = "Restaurant"
                    )
                )
                checked=true
            }
            else{
                res_favBtn.colorFilter = null
                checked=false

                reference!!.child(obj.imageURL).addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (appleSnapshot in snapshot.children) {
                            appleSnapshot.ref.removeValue()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("Error","Failed to remove")
                    }
                })
            }
        }

        res_name.text = obj.Name
        res_desc.text=obj.Description
        //ResType.text = type
        var result = when (obj.imageURL) {
            "pic1" -> R.drawable.pic1
            "pic2" -> R.drawable.pic2
            "pic6" -> R.drawable.pic6
            "pic7" -> R.drawable.pic7
            else -> R.drawable.pic10
        }
        res_pic.setImageResource(result)
        res_rat.rating = obj.Rating.toFloat()
    }

    fun onClick(v: View) {
        res_detailLayout.visibility = GONE
        res_reviewLayout.visibility = GONE
        res_mapLayout.visibility = GONE
        resbtn_holder.visibility= GONE
        res_mapBtn.visibility = GONE
        res_favBtn.visibility = GONE
        res_button3.setBackgroundResource(R.color.white)
        res_button2.setBackgroundResource(R.color.white)
        res_button4.setBackgroundResource(R.color.white)
        when (v.id) {
            R.id.res_button2 -> {
                res_detailLayout.visibility = VISIBLE
                resbtn_holder.visibility= VISIBLE
                res_favBtn.visibility = VISIBLE
                res_button2.setBackgroundResource(R.color.secondary)
            }
            R.id.res_button4 -> {
                res_mapLayout.visibility = VISIBLE
                resbtn_holder.visibility= VISIBLE
                res_mapBtn.visibility = VISIBLE
                res_favBtn.visibility = GONE
                res_button4.setBackgroundResource(R.color.secondary)
            }
            R.id.res_button3 -> {
                res_reviewLayout.visibility = VISIBLE
                res_button3.setBackgroundResource(R.color.secondary)
            }

        }


    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        latLng = LatLng(obj.Latitude, obj.Longitude)
        mMap.addMarker(MarkerOptions().position(latLng).title(obj.Name))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))

    }

    override fun onDataChange(snapshot: DataSnapshot) {
        if (snapshot.value !== null) {
            res_favBtn.setColorFilter(
                ContextCompat.getColor(baseContext, R.color.red),
                android.graphics.PorterDuff.Mode.SRC_IN
            );
            checked=true
        }
    }

    override fun onCancelled(error: DatabaseError) {
        Log.d("Error","Failed to load")
    }
}
