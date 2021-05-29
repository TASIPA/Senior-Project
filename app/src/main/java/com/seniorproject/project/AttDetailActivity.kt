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
import com.seniorproject.project.R.color
import com.seniorproject.project.R.color.*
import com.seniorproject.project.models.Favorite
import com.seniorproject.project.models.Restaurants
import kotlinx.android.synthetic.main.activity_ame_detail.*
import kotlinx.android.synthetic.main.activity_att_detail.*
import kotlinx.android.synthetic.main.activity_res_detail.*


class AttDetailActivity : AppCompatActivity(), OnMapReadyCallback, ValueEventListener {

    private lateinit var mMap: GoogleMap
    private lateinit var latLng: LatLng

    var rootNode: FirebaseDatabase? = null
    var reference: DatabaseReference? = null
    var auth: FirebaseAuth? = null
    var checked: Boolean = false
    lateinit var obj: Restaurants

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_att_detail)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.att_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //firebase hooks
        auth = FirebaseAuth.getInstance()
        var currentuser = auth!!.currentUser!!.uid
        rootNode = FirebaseDatabase.getInstance()
        reference = rootNode!!.getReference("favorite").child(currentuser)
        obj= intent.getSerializableExtra("attObj") as Restaurants
        //intent value


        //calls onDataChanged()
        reference!!.child(obj.id.toString()).addListenerForSingleValueEvent(this)

        att_favBtn.setOnClickListener {
            if (!checked) {
                att_favBtn.setColorFilter(
                    ContextCompat.getColor(baseContext, red), PorterDuff.Mode.SRC_IN
                )
//                reference!!.child(pic).setValue(Favorite(name,pic, type, rating = 4.5, distance = 0.0,
//                    id = "Attraction"
//                ))
                reference!!.child(obj.id.toString()).setValue(obj)
                checked = true
            } else {
                att_favBtn.colorFilter = null
                checked = false
                reference!!.child(obj.id.toString()).addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (appleSnapshot in snapshot.children) {
                            appleSnapshot.ref.removeValue()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("Error", "Failed to remove")
                    }
                })
            }
        }

        att_name.text = obj.Name
        att_loc.text = obj.Location
        att_type.text = obj.Category
        var result = when (obj.imageURL) {
            "attpic1" -> R.drawable.attpic1
            "attpic2" -> R.drawable.attpic2
            "attpic3" -> R.drawable.attpic3
            "attpic4" -> R.drawable.attpic5
            else -> R.drawable.epic2
        }
        att_pic.setImageResource(result)
        att_rat.rating = obj.Rating.toFloat()
        att_ratVal.text=obj.Rating.toString()
        att_type.text=obj.Category
        att_loc.text=obj.Location
    }

    fun onClick(v: View) {
        attdetailLayout.visibility = GONE
        att_reviewLayout.visibility = GONE
        attmapLayout.visibility = GONE
        att_mapBtn.visibility = GONE
        attBtn_holder.visibility =GONE
        att_favBtn.visibility = GONE
        attbutton3.setBackgroundResource(white)
        attbutton2.setBackgroundResource(white)
        attbutton4.setBackgroundResource(white)
        when (v.id) {
            R.id.attbutton2 -> {
                attdetailLayout.visibility = VISIBLE
                attBtn_holder.visibility = VISIBLE
                att_favBtn.visibility = VISIBLE
                attbutton2.setBackgroundResource(secondary)
            }
            R.id.attbutton4 -> {
                attmapLayout.visibility = VISIBLE
                attBtn_holder.visibility = VISIBLE
                att_mapBtn.visibility = VISIBLE
                attbutton4.setBackgroundResource(secondary)
            }
            R.id.attbutton3 -> {
                att_reviewLayout.visibility = VISIBLE
                attbutton3.setBackgroundResource(secondary)
            }

        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        latLng = LatLng(obj.Latitude,obj.Longitude)
        // Add a marker in Sydney and move the camera
        mMap.addMarker(MarkerOptions().position(latLng).title(obj.Name))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        if (snapshot.value !== null) {
            att_favBtn.setColorFilter(
                ContextCompat.getColor(baseContext, red),
                android.graphics.PorterDuff.Mode.SRC_IN
            );
            checked = true
        }
    }

    override fun onCancelled(error: DatabaseError) {
        Log.d("Error", "Failed to load")
    }
}