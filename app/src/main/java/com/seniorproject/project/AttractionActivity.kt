package com.seniorproject.project

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.seniorproject.project.Adapters.AttractionAdapter
import com.seniorproject.project.Interface.onItemClickListener
import com.seniorproject.project.models.Restaurants
import kotlinx.android.synthetic.main.activity_attraction.*
import kotlinx.android.synthetic.main.activity_attraction.back_btn



class AttractionActivity : AppCompatActivity(),onItemClickListener {
    lateinit var attdata:MutableList<Restaurants>
    lateinit var db: FirebaseFirestore
    lateinit var adapter: AttractionAdapter
    var flag=true

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var currentLatLng: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attraction)
        supportActionBar?.hide()
        back_btn.setOnClickListener {
            finish()
        }
        attdata= mutableListOf()
        db= FirebaseFirestore.getInstance()
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL,false)
        attList.layoutManager = linearLayoutManager
        //replace with event adapter
        val docRef = db.collection("Attractions")
        docRef.get()//ordering ...
            .addOnSuccessListener { snapShot ->//this means if read is successful then this data will be loaded to snapshot
                if (snapShot != null) {
                    attdata!!.clear()
                    attdata = snapShot.toObjects(Restaurants::class.java)
                    var i=0
                    for (ame in attdata ){

                        val loc1 = Location("")
                        loc1.setLatitude(currentLatLng.latitude)
                        loc1.setLongitude(currentLatLng.longitude)

                        val loc2 = Location("")
                        loc2.setLatitude(ame.Latitude)
                        loc2.setLongitude(ame.Longitude)

                        val distanceInMeters: Float = loc1.distanceTo(loc2)
                        var distanceInKm = String.format("%.2f", (distanceInMeters / 1000)).toFloat()
                        attdata[i].CalculatedDis=distanceInKm
                        i+=1
                    }
                    attdata.sortBy { it.CalculatedDis }
                    adapter = AttractionAdapter(currentLatLng,attdata, baseContext,this)
                    attList.adapter=adapter
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
        att_search.addTextChangedListener(object : TextWatcher {
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

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                //textView1.text = location.latitude.toString() + ", " + location.longitude.toString()

                if (location == null){
                    Toast.makeText(applicationContext, "Location Not Found",Toast.LENGTH_SHORT).show()
                }
                else{
                    currentLatLng = LatLng(location.latitude,location.longitude)

                }
            }

            override fun onProviderDisabled(provider: String) {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
        requestLocation()
    }

    private fun requestLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),10)
            }
            return
        }
        locationManager.requestLocationUpdates("gps",1000,0f,locationListener)
//        gpsBtn.setOnClickListener{
//            if (currentLatLng!=null){
//                latText.setText(currentLatLng.latitude.toString())
//                longText.setText(currentLatLng.longitude.toString())
//            }
//        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode){
            10 -> requestLocation()
            else -> Toast.makeText(this,"Do not nothing (becuz the requestCode != 10)", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        locationManager.removeUpdates(locationListener)
        Log.i("GPS Status","pause")
    }

    override fun onItemClick(position: Int,data:MutableList<Restaurants>) {
        var intent= Intent(this,AttDetailActivity::class.java)
        intent.putExtra("attObj",data[position])
        startActivity(intent)

    }
}
