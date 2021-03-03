package com.seniorproject.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_ame_detail.*
import kotlinx.android.synthetic.main.activity_ame_detail.AmeName
import kotlinx.android.synthetic.main.activity_res_detail.*

class ResDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var latLng: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_res_detail)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val bundle = intent.extras
        //var pointLat = bundle?.getString("lati").toString().toDouble()
        //var pointLon = bundle?.getString("longi").toString().toDouble()
        //use to calculate distance to User
        var name = bundle?.getString("name").toString()
        var type = bundle?.getString("type").toString()
        var pic = bundle?.getString("image").toString()
        var rate = bundle?.getString("rating").toString()

        ResName.text = name
        ResType.text = type
        var result = when (pic) {
            "pic1" -> R.drawable.pic1
            "pic2" -> R.drawable.pic2
            "pic3" -> R.drawable.pic3
            "pic4" -> R.drawable.pic4
            else -> R.drawable.pic5
        }
        ResPic.setImageResource(result)
        ResratingBar.rating = rate!!.toFloat()
    }

/*
*
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
        val bundle = intent.extras
        var pointLat = 13.7889129
        var pointLon = 100.3233457
        var name = "hell"
        latLng = LatLng(pointLat, pointLon)
        // Add a marker in Sydney and move the camera
        mMap.addMarker(MarkerOptions().position(latLng).title(name))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
        if (name == null) {
            finish()
        }
    }
}
