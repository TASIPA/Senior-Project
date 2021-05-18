package com.seniorproject.project

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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

import kotlinx.android.synthetic.main.activity_res_detail.*

class ResDetailActivity : AppCompatActivity(), OnMapReadyCallback, ValueEventListener {

    private lateinit var mMap: GoogleMap
    private lateinit var latLng: LatLng

    var rootNode: FirebaseDatabase? = null
    var reference: DatabaseReference? = null
    var auth: FirebaseAuth? = null
    var checked: Boolean=false


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
        val bundle = intent.extras
        var name = bundle?.getString("name").toString()
        var type = bundle?.getString("type").toString()
        var pic = bundle?.getString("image").toString()
        var rate = bundle?.getString("rating").toString()
        //calls onDataChanged()
        reference!!.child(pic).addListenerForSingleValueEvent(this)
        res_favBtn.setOnClickListener {
            if (!checked) {
                res_favBtn.setColorFilter(
                    ContextCompat.getColor(baseContext, R.color.red), PorterDuff.Mode.SRC_IN
                )
                reference!!.child(pic).setValue(
                    Favorite(
                        name,
                        pic,
                        type,
                        rating = 4.5,
                        distance = 0.0,
                        id = "Restaurant"
                    )
                )
                checked=true
            }
            else{
                res_favBtn.colorFilter = null
                checked=false

                reference!!.child(pic).addListenerForSingleValueEvent(object : ValueEventListener {

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

        res_name.text = name
        //ResType.text = type
        var result = when (pic) {
            "pic1" -> R.drawable.pic1
            "pic2" -> R.drawable.pic2
            "pic6" -> R.drawable.pic6
            "pic7" -> R.drawable.pic7
            else -> R.drawable.pic10
        }
        res_pic.setImageResource(result)
        //ResratingBar.rating = rate!!.toFloat()
    }

    fun onClick(v: View) {
        res_detailLayout.visibility = View.GONE
        res_reviewLayout.visibility = View.GONE
        res_mapLayout.visibility = View.GONE
        res_mapBtn.visibility = View.GONE
        res_favBtn.visibility = View.GONE
        res_button3.setBackgroundResource(R.color.white)
        res_button2.setBackgroundResource(R.color.white)
        res_button4.setBackgroundResource(R.color.white)
        when (v.id) {
            R.id.res_button2 -> {
                res_detailLayout.visibility = View.VISIBLE
                res_favBtn.visibility = View.VISIBLE
                res_button2.setBackgroundResource(R.color.secondary)
            }
            R.id.res_button4 -> {
                res_mapLayout.visibility = View.VISIBLE
                res_mapBtn.visibility = View.VISIBLE
                res_favBtn.visibility = View.GONE
                res_button4.setBackgroundResource(R.color.secondary)
            }
            R.id.res_button3 -> {
                res_reviewLayout.visibility = View.VISIBLE
                res_button3.setBackgroundResource(R.color.secondary)
            }

        }


    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //val bundle = intent.extras
        var pointLat = 13.7530819
        var pointLon = 100.5022286
        var name = "Kope Hya Tai Kee"
        latLng = LatLng(pointLat, pointLon)
        // Add a marker in Sydney and move the camera
        mMap.addMarker(MarkerOptions().position(latLng).title(name))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
        if (name == null) {
            finish()
        }
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
