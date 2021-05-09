package com.seniorproject.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_ame_detail.*
import kotlinx.android.synthetic.main.activity_ame_detail.AmeName
import kotlinx.android.synthetic.main.activity_ame_detail.amebutton2
import kotlinx.android.synthetic.main.activity_ame_detail.amebutton3
import kotlinx.android.synthetic.main.activity_ame_detail.amebutton4
import kotlinx.android.synthetic.main.activity_ame_detail.amedetailLayout
import kotlinx.android.synthetic.main.activity_ame_detail.amemapLayout
import kotlinx.android.synthetic.main.activity_ame_detail.amereviewLayout
import kotlinx.android.synthetic.main.activity_att_detail.*

class AttDetailActivity : AppCompatActivity() , OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var latLng: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_att_detail)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.attmap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        attdetailLayout.visibility = View.VISIBLE
        attreviewLayout.visibility = View.GONE
        attmapLayout.visibility = View.GONE

        attbutton2.setOnClickListener {
            attdetailLayout.visibility = View.VISIBLE
            attreviewLayout.visibility = View.GONE
            attmapLayout.visibility = View.GONE
        }

        attbutton3.setOnClickListener {
            attdetailLayout.visibility = View.GONE
            attreviewLayout.visibility = View.VISIBLE
            attmapLayout.visibility = View.GONE
        }

        attbutton4.setOnClickListener {
            attdetailLayout.visibility = View.GONE
            attreviewLayout.visibility = View.GONE
            attmapLayout.visibility = View.VISIBLE
        }

        val bundle = intent.extras
        /*var pointLat = bundle?.getString("lati").toString().toDouble()
        var pointLon = bundle?.getString("longi").toString().toDouble()*/
        //use to calculate distance to User
        var name = bundle?.getString("name").toString()
        var type = bundle?.getString("type").toString()
        var pic = bundle?.getString("image").toString()


        AttName.text = name
        var result=when (pic) {
            "attpic1" -> R.drawable.attpic1
            "attpic2" -> R.drawable.attpic2
            "attpic3"-> R.drawable.attpic3
            "attpic4"-> R.drawable.attpic5
            else -> R.drawable.epic2
        }
        AttPic.setImageResource(result)
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