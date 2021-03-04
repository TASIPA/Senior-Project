package com.seniorproject.project.ui.Emergency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.seniorproject.project.R

class EmergencyFragment : Fragment(), OnMapReadyCallback {

    private lateinit var emergencyViewModel: EmergencyViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var latLng: LatLng

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        emergencyViewModel =
                ViewModelProvider(this).get(EmergencyViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_emergency, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        emergencyViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //val bundle = intent.extras
        var pointLat = 13.7889129
        var pointLon = 100.3233457
        var name = "hell"
        latLng = LatLng(pointLat,pointLon)
        // Add a marker in Sydney and move the camera
        mMap.addMarker(MarkerOptions().position(latLng).title(name))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

    }
}