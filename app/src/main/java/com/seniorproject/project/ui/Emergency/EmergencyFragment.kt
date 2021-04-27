package com.seniorproject.project.ui.Emergency

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
//import com.seniorproject.project.GetNearbyPlacesData
import com.seniorproject.project.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_emergency.*


class EmergencyFragment : Fragment(), OnMapReadyCallback {

    private lateinit var emergencyViewModel: EmergencyViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var mapView: MapView
    //private lateinit var callPol: Button
    //private lateinit var callFire: Button
    private lateinit var latLng: LatLng
    private lateinit var latLng1: LatLng
    private lateinit var latLng2: LatLng
    private lateinit var latLng3: LatLng
    private lateinit var latLng4: LatLng

    private var showHos = 0

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        emergencyViewModel =
                ViewModelProvider(this).get(EmergencyViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_emergency, container, false)
        //val textView: TextView = root.findViewById(R.id.text_dashboard)
        emergencyViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })
        //callPol = root.findViewById(R.id.callPoliceBtn)
        //callFire = root.findViewById(R.id.callFireBtn)
//        callPoliceBtn.setImageResource(R.drawable.policephone)
//        callFireBtn.setImageResource(R.drawable.firephone)

        mapView = root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onResume(); // needed to get the map to display immediately
        mapView.getMapAsync(this)

        return root
    }

    override fun onStart() {
        super.onStart()

        var hos1lat = 13.786933
        var hos1lon = 100.321401
        var hos2lat = 13.804747
        var hos2lon = 100.303758
        var hos3lat = 13.798655
        var hos3lon = 100.288209
        var hos4lat = 13.805051
        var hos4lon = 100.284710

        latLng1 = LatLng(hos1lat, hos1lon)
        latLng2 = LatLng(hos2lat, hos2lon)
        latLng3 = LatLng(hos3lat, hos3lon)
        latLng4 = LatLng(hos4lat, hos4lon)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.getUiSettings().setMyLocationButtonEnabled(false)
        //mMap.setMyLocationEnabled(true)
        //val bundle = intent.extras
        var pointLat = 13.7889129
        var pointLon = 100.3233457
        var name = showHos.toString()
        latLng = LatLng(pointLat, pointLon)

        HospitalShowBtn.setOnClickListener {
            mMap.addMarker(MarkerOptions().position(latLng1).title("Maha Chakri Sirindhorn Dental Hospital)"))
            mMap.addMarker(MarkerOptions().position(latLng2).title("Salaya Hospital"))
            mMap.addMarker(MarkerOptions().position(latLng3).title("Surawan Health Promoting Hospital"))
            mMap.addMarker(MarkerOptions().position(latLng4).title("Phutthamonthon Hospital"))
        }

        mMap.addMarker(MarkerOptions().position(latLng).title(name))

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))

    }



//    override fun onClick(v: View){
//        val btnHos = v.findViewById(R.id.showHosBtn) as ImageButton
//        btnHos.setOnClickListener(object : View.OnClickListener {
//            var Restaurant = "restaurant"
//            override fun onClick(v: View) {
//                Log.d("onClick", "Button is Clicked")
//                mMap.clear()
//                //val url: String = getUrl(latitude, longitude, Restaurant)
//                val DataTransfer = arrayOfNulls<Any>(2)
//                DataTransfer[0] = mMap
//                DataTransfer[1] = url
//                Log.d("onClick", url)
//                val getNearbyPlacesData = GetNearbyPlacesData()
//                getNearbyPlacesData.execute(DataTransfer)
//                Toast.makeText(this, "Nearby Restaurants", Toast.LENGTH_LONG).show()
//            }
//        })
//    }
}