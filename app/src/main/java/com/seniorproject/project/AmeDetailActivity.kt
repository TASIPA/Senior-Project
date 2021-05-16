package com.seniorproject.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.seniorproject.project.models.FavObj

import kotlinx.android.synthetic.main.activity_ame_detail.*
import kotlinx.android.synthetic.main.activity_res_detail.*

class AmeDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var latLng: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ame_detail)
        supportActionBar?.hide()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.amemap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        amedetailLayout.visibility = View.VISIBLE
        amereviewLayout.visibility = View.GONE
        amemapLayout.visibility = View.GONE

        amebutton2.setOnClickListener {
            amedetailLayout.visibility = View.VISIBLE
            amereviewLayout.visibility = View.GONE
            amemapLayout.visibility = View.GONE
        }

        amebutton3.setOnClickListener {
            amedetailLayout.visibility = View.GONE
            amereviewLayout.visibility = View.VISIBLE
            amemapLayout.visibility = View.GONE
        }

        amebutton4.setOnClickListener {
            amedetailLayout.visibility = View.GONE
            amereviewLayout.visibility = View.GONE
            amemapLayout.visibility = View.VISIBLE
        }

        val bundle = intent.extras
        /*var pointLat = bundle?.getString("lati").toString().toDouble()
        var pointLon = bundle?.getString("longi").toString().toDouble()*/
        //use to calculate distance to User
        var name = bundle?.getString("name").toString()
        var type = bundle?.getString("type").toString()
        var pic = bundle?.getString("image").toString()

        fav_ameBtn.setOnClickListener {
            fav_ameBtn.setColorFilter(ContextCompat.getColor(baseContext, R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
            FavObj.addData(name,pic,type)
        }
        AmeName.text = name
        //AmeType.text = type
        var result=when (pic) {
            "apic1" -> R.drawable.apic1
            "apic2" -> R.drawable.apic2
            "apic3"-> R.drawable.apic3
            "apic4"-> R.drawable.apic4
            else -> R.drawable.epic5
        }
        AmePic.setImageResource(result)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //val bundle = intent.extras
        var pointLat = 13.7998783
        var pointLon = 100.3113113
        var name = "7-11"
        latLng = LatLng(pointLat,pointLon)
        // Add a marker in Sydney and move the camera
        mMap.addMarker(MarkerOptions().position(latLng).title(name))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        if (name==null) {
            finish()
        }
    }
}