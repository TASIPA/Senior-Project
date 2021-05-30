package com.seniorproject.project

import android.Manifest
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.seniorproject.project.Adapters.*
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

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var currentLatLng: LatLng

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
                    adapter = EventAdapter(currentLatLng,evedata, baseContext,this)
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

        //currentLatLng = LatLng(0.0,0.0)

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

    override fun onItemClick(position: Int, data: MutableList<Events>) {
        var intent= Intent(this,EveDetailActivity::class.java)
        intent.putExtra("eveObj",data[position])
        //intent.putExtra("rating",res[position].rating.toString())
        startActivity(intent)
    }
}
