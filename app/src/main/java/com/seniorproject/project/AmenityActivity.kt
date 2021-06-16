package com.seniorproject.project

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import com.google.firebase.firestore.Query
import com.seniorproject.project.Adapters.AmenityAdapter
import com.seniorproject.project.Adapters.RestaurantAdapter
import com.seniorproject.project.Interface.onItemClickListener
import com.seniorproject.project.models.Restaurants
import kotlinx.android.synthetic.main.activity_amenity.*
import kotlinx.android.synthetic.main.activity_amenity.all_txt
import kotlinx.android.synthetic.main.activity_amenity.back_btn
import kotlinx.android.synthetic.main.activity_amenity.search_button
import kotlinx.android.synthetic.main.activity_amenity.search_view
import kotlinx.android.synthetic.main.activity_amenity.sort_button
import kotlinx.android.synthetic.main.activity_restaurant.*


class AmenityActivity : AppCompatActivity(),onItemClickListener {
    lateinit var amedata:MutableList<Restaurants>
    lateinit var db: FirebaseFirestore
    lateinit var adapter: AmenityAdapter
    var flag=true
    private lateinit var dialog: Dialog

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private  var currentLatLng: LatLng= LatLng(0.0,0.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amenity)
       supportActionBar?.hide()
        back_btn.setOnClickListener {
            finish()
        }
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL,false)
        amenList.layoutManager = linearLayoutManager
        amedata= mutableListOf()
        dialog = Dialog(this)
        db= FirebaseFirestore.getInstance()
        readAll()
        sort_button.setOnClickListener {
            dialog.setContentView(R.layout.sort_card)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

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
        ame_search.addTextChangedListener(object : TextWatcher {
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
    fun readAll(){
        all_txt.setBackgroundResource(R.color.secondary)
        Bank_txt.setBackgroundResource(R.color.white)
        BeautySalon_txt.setBackgroundResource(R.color.white)
        CopyShop_txt.setBackgroundResource(R.color.white)
        ClothingStore_txt.setBackgroundResource(R.color.white)
        DentalClinic_txt.setBackgroundResource(R.color.white)
        Fuel_txt.setBackgroundResource(R.color.white)
        HairSalon_txt.setBackgroundResource(R.color.white)
        Pharmacy_txt.setBackgroundResource(R.color.white)
        GameStore_txt.setBackgroundResource(R.color.white)
        MassageSpa_txt.setBackgroundResource(R.color.white)
        PostOffice_txt.setBackgroundResource(R.color.white)
        AmeOther_txt.setBackgroundResource(R.color.white)
        val docRef = db.collection("Amenities")
        docRef.get()//ordering ...
            .addOnSuccessListener { snapShot ->//this means if read is successful then this data will be loaded to snapshot
                if (snapShot != null) {
                    amedata.clear()
                    amedata = snapShot.toObjects(Restaurants::class.java)
                    adapter = AmenityAdapter(currentLatLng, amedata, baseContext,this)
                    amenList.adapter=adapter
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
    }
    fun dis_sorting(view: View) {
        dialog.dismiss()
        var i=0
        for (ame in amedata ){

            val loc1 = Location("")
            loc1.setLatitude(currentLatLng.latitude)
            loc1.setLongitude(currentLatLng.longitude)

            val loc2 = Location("")
            loc2.setLatitude(ame.Latitude)
            loc2.setLongitude(ame.Longitude)

            val distanceInMeters: Float = loc1.distanceTo(loc2)
            var distanceInKm = String.format("%.2f", (distanceInMeters / 1000)).toFloat()
            amedata[i].CalculatedDis=distanceInKm
            i+=1
        }
        amedata.sortBy { it.CalculatedDis }
        adapter = AmenityAdapter(currentLatLng, amedata, baseContext,this)
        amenList.adapter=adapter
    }
    fun rat_sorting(view: View) {
        dialog.dismiss()
        amedata.sortByDescending { it.Rating }
        adapter = AmenityAdapter(currentLatLng, amedata, baseContext,this)
        amenList.adapter=adapter

    }

    override fun onItemClick(position: Int,data:MutableList<Restaurants>) {
        var intent= Intent(this,AmeDetailActivity::class.java)
        intent.putExtra("ameObj",data[position])
        startActivity(intent)

    }

    fun filterbyAll(view: View) {
        readAll()
    }
    fun filterbyBank(view: View) {
        all_txt.setBackgroundResource(R.color.white)
        Bank_txt.setBackgroundResource(R.color.secondary)
        BeautySalon_txt.setBackgroundResource(R.color.white)
        CopyShop_txt.setBackgroundResource(R.color.white)
        ClothingStore_txt.setBackgroundResource(R.color.white)
        DentalClinic_txt.setBackgroundResource(R.color.white)
        Fuel_txt.setBackgroundResource(R.color.white)
        HairSalon_txt.setBackgroundResource(R.color.white)
        Pharmacy_txt.setBackgroundResource(R.color.white)
        GameStore_txt.setBackgroundResource(R.color.white)
        MassageSpa_txt.setBackgroundResource(R.color.white)
        PostOffice_txt.setBackgroundResource(R.color.white)
        AmeOther_txt.setBackgroundResource(R.color.white)
        db.collection("Amenities").whereEqualTo("Category","Bank")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    amedata.clear()
                    amedata = it.toObjects(Restaurants::class.java)
                    adapter = AmenityAdapter(currentLatLng, amedata, baseContext,this)
                    amenList.adapter=adapter
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
    }
    fun filterbyBeautySalon(view: View) {
        all_txt.setBackgroundResource(R.color.white)
        Bank_txt.setBackgroundResource(R.color.white)
        BeautySalon_txt.setBackgroundResource(R.color.secondary)
        CopyShop_txt.setBackgroundResource(R.color.white)
        ClothingStore_txt.setBackgroundResource(R.color.white)
        DentalClinic_txt.setBackgroundResource(R.color.white)
        Fuel_txt.setBackgroundResource(R.color.white)
        HairSalon_txt.setBackgroundResource(R.color.white)
        Pharmacy_txt.setBackgroundResource(R.color.white)
        GameStore_txt.setBackgroundResource(R.color.white)
        MassageSpa_txt.setBackgroundResource(R.color.white)
        PostOffice_txt.setBackgroundResource(R.color.white)
        AmeOther_txt.setBackgroundResource(R.color.white)
        db.collection("Amenities").whereEqualTo("Category","Beauty Salon")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    amedata.clear()
                    amedata = it.toObjects(Restaurants::class.java)
                    adapter = AmenityAdapter(currentLatLng, amedata, baseContext,this)
                    amenList.adapter=adapter
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
    }
    fun filterbyCopyShop(view: View) {
        all_txt.setBackgroundResource(R.color.white)
        Bank_txt.setBackgroundResource(R.color.white)
        BeautySalon_txt.setBackgroundResource(R.color.white)
        CopyShop_txt.setBackgroundResource(R.color.secondary)
        ClothingStore_txt.setBackgroundResource(R.color.white)
        DentalClinic_txt.setBackgroundResource(R.color.white)
        Fuel_txt.setBackgroundResource(R.color.white)
        HairSalon_txt.setBackgroundResource(R.color.white)
        Pharmacy_txt.setBackgroundResource(R.color.white)
        GameStore_txt.setBackgroundResource(R.color.white)
        MassageSpa_txt.setBackgroundResource(R.color.white)
        PostOffice_txt.setBackgroundResource(R.color.white)
        AmeOther_txt.setBackgroundResource(R.color.white)
        db.collection("Amenities").whereEqualTo("Category","Copy Shop")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    amedata.clear()
                    amedata = it.toObjects(Restaurants::class.java)
                    adapter = AmenityAdapter(currentLatLng, amedata, baseContext,this)
                    amenList.adapter=adapter
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
    }
    fun filterbyClothingStore(view: View) {
        all_txt.setBackgroundResource(R.color.white)
        Bank_txt.setBackgroundResource(R.color.white)
        BeautySalon_txt.setBackgroundResource(R.color.white)
        CopyShop_txt.setBackgroundResource(R.color.white)
        ClothingStore_txt.setBackgroundResource(R.color.secondary)
        DentalClinic_txt.setBackgroundResource(R.color.white)
        Fuel_txt.setBackgroundResource(R.color.white)
        HairSalon_txt.setBackgroundResource(R.color.white)
        Pharmacy_txt.setBackgroundResource(R.color.white)
        GameStore_txt.setBackgroundResource(R.color.white)
        MassageSpa_txt.setBackgroundResource(R.color.white)
        PostOffice_txt.setBackgroundResource(R.color.white)
        AmeOther_txt.setBackgroundResource(R.color.white)
        db.collection("Amenities").whereEqualTo("Category","Clothing Store")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    amedata.clear()
                    amedata = it.toObjects(Restaurants::class.java)
                    adapter = AmenityAdapter(currentLatLng, amedata, baseContext,this)
                    amenList.adapter=adapter
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
    }
    fun filterbyDentalClinic(view: View) {
        all_txt.setBackgroundResource(R.color.white)
        Bank_txt.setBackgroundResource(R.color.white)
        BeautySalon_txt.setBackgroundResource(R.color.white)
        CopyShop_txt.setBackgroundResource(R.color.white)
        ClothingStore_txt.setBackgroundResource(R.color.white)
        DentalClinic_txt.setBackgroundResource(R.color.secondary)
        Fuel_txt.setBackgroundResource(R.color.white)
        HairSalon_txt.setBackgroundResource(R.color.white)
        Pharmacy_txt.setBackgroundResource(R.color.white)
        GameStore_txt.setBackgroundResource(R.color.white)
        MassageSpa_txt.setBackgroundResource(R.color.white)
        PostOffice_txt.setBackgroundResource(R.color.white)
        AmeOther_txt.setBackgroundResource(R.color.white)
        db.collection("Amenities").whereEqualTo("Category","Dental Clinic")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    amedata.clear()
                    amedata = it.toObjects(Restaurants::class.java)
                    adapter = AmenityAdapter(currentLatLng, amedata, baseContext,this)
                    amenList.adapter=adapter
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
    }
    fun filterbyFuel(view: View) {
        all_txt.setBackgroundResource(R.color.white)
        Bank_txt.setBackgroundResource(R.color.white)
        BeautySalon_txt.setBackgroundResource(R.color.white)
        CopyShop_txt.setBackgroundResource(R.color.white)
        ClothingStore_txt.setBackgroundResource(R.color.white)
        DentalClinic_txt.setBackgroundResource(R.color.white)
        Fuel_txt.setBackgroundResource(R.color.secondary)
        HairSalon_txt.setBackgroundResource(R.color.white)
        Pharmacy_txt.setBackgroundResource(R.color.white)
        GameStore_txt.setBackgroundResource(R.color.white)
        MassageSpa_txt.setBackgroundResource(R.color.white)
        PostOffice_txt.setBackgroundResource(R.color.white)
        AmeOther_txt.setBackgroundResource(R.color.white)
        db.collection("Amenities").whereEqualTo("Category","Fuel")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    amedata.clear()
                    amedata = it.toObjects(Restaurants::class.java)
                    adapter = AmenityAdapter(currentLatLng, amedata, baseContext,this)
                    amenList.adapter=adapter
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
    }
    fun filterbyHairSalon(view: View) {
        all_txt.setBackgroundResource(R.color.white)
        Bank_txt.setBackgroundResource(R.color.white)
        BeautySalon_txt.setBackgroundResource(R.color.white)
        CopyShop_txt.setBackgroundResource(R.color.white)
        ClothingStore_txt.setBackgroundResource(R.color.white)
        DentalClinic_txt.setBackgroundResource(R.color.white)
        Fuel_txt.setBackgroundResource(R.color.white)
        HairSalon_txt.setBackgroundResource(R.color.secondary)
        Pharmacy_txt.setBackgroundResource(R.color.white)
        GameStore_txt.setBackgroundResource(R.color.white)
        MassageSpa_txt.setBackgroundResource(R.color.white)
        PostOffice_txt.setBackgroundResource(R.color.white)
        AmeOther_txt.setBackgroundResource(R.color.white)
        db.collection("Amenities").whereEqualTo("Category","Hair Salon")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    amedata.clear()
                    amedata = it.toObjects(Restaurants::class.java)
                    adapter = AmenityAdapter(currentLatLng, amedata, baseContext,this)
                    amenList.adapter=adapter
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

    }
    fun filterbyPharmacy(view: View) {
        all_txt.setBackgroundResource(R.color.white)
        Bank_txt.setBackgroundResource(R.color.white)
        BeautySalon_txt.setBackgroundResource(R.color.white)
        CopyShop_txt.setBackgroundResource(R.color.white)
        ClothingStore_txt.setBackgroundResource(R.color.white)
        DentalClinic_txt.setBackgroundResource(R.color.white)
        Fuel_txt.setBackgroundResource(R.color.white)
        HairSalon_txt.setBackgroundResource(R.color.white)
        Pharmacy_txt.setBackgroundResource(R.color.secondary)
        GameStore_txt.setBackgroundResource(R.color.white)
        MassageSpa_txt.setBackgroundResource(R.color.white)
        PostOffice_txt.setBackgroundResource(R.color.white)
        AmeOther_txt.setBackgroundResource(R.color.white)
        db.collection("Amenities").whereEqualTo("Category","Pharmacy")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    amedata.clear()
                    amedata = it.toObjects(Restaurants::class.java)
                    adapter = AmenityAdapter(currentLatLng, amedata, baseContext,this)
                    amenList.adapter=adapter
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
    }
    fun filterbyGameStore(view: View) {
        all_txt.setBackgroundResource(R.color.white)
        Bank_txt.setBackgroundResource(R.color.white)
        BeautySalon_txt.setBackgroundResource(R.color.white)
        CopyShop_txt.setBackgroundResource(R.color.white)
        ClothingStore_txt.setBackgroundResource(R.color.white)
        DentalClinic_txt.setBackgroundResource(R.color.white)
        Fuel_txt.setBackgroundResource(R.color.white)
        HairSalon_txt.setBackgroundResource(R.color.white)
        Pharmacy_txt.setBackgroundResource(R.color.white)
        GameStore_txt.setBackgroundResource(R.color.secondary)
        MassageSpa_txt.setBackgroundResource(R.color.white)
        PostOffice_txt.setBackgroundResource(R.color.white)
        AmeOther_txt.setBackgroundResource(R.color.white)
        db.collection("Amenities").whereEqualTo("Category","Game Store")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    amedata.clear()
                    amedata = it.toObjects(Restaurants::class.java)
                    adapter = AmenityAdapter(currentLatLng, amedata, baseContext,this)
                    amenList.adapter=adapter
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
    }
    fun filterbyMassageSpa(view: View) {
        all_txt.setBackgroundResource(R.color.white)
        Bank_txt.setBackgroundResource(R.color.white)
        BeautySalon_txt.setBackgroundResource(R.color.white)
        CopyShop_txt.setBackgroundResource(R.color.white)
        ClothingStore_txt.setBackgroundResource(R.color.white)
        DentalClinic_txt.setBackgroundResource(R.color.white)
        Fuel_txt.setBackgroundResource(R.color.white)
        HairSalon_txt.setBackgroundResource(R.color.white)
        Pharmacy_txt.setBackgroundResource(R.color.white)
        GameStore_txt.setBackgroundResource(R.color.white)
        MassageSpa_txt.setBackgroundResource(R.color.secondary)
        PostOffice_txt.setBackgroundResource(R.color.white)
        AmeOther_txt.setBackgroundResource(R.color.white)
        db.collection("Amenities").whereEqualTo("Category","Massage")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    amedata.clear()
                    amedata = it.toObjects(Restaurants::class.java)
                    adapter = AmenityAdapter(currentLatLng, amedata, baseContext,this)
                    amenList.adapter=adapter
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
    }
    fun filterbyPostOffice(view: View) {
        all_txt.setBackgroundResource(R.color.white)
        Bank_txt.setBackgroundResource(R.color.white)
        BeautySalon_txt.setBackgroundResource(R.color.white)
        CopyShop_txt.setBackgroundResource(R.color.white)
        ClothingStore_txt.setBackgroundResource(R.color.white)
        DentalClinic_txt.setBackgroundResource(R.color.white)
        Fuel_txt.setBackgroundResource(R.color.white)
        HairSalon_txt.setBackgroundResource(R.color.white)
        Pharmacy_txt.setBackgroundResource(R.color.white)
        GameStore_txt.setBackgroundResource(R.color.white)
        MassageSpa_txt.setBackgroundResource(R.color.white)
        PostOffice_txt.setBackgroundResource(R.color.secondary)
        AmeOther_txt.setBackgroundResource(R.color.white)
        db.collection("Amenities").whereEqualTo("Category","Post Office")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    amedata.clear()
                    amedata = it.toObjects(Restaurants::class.java)
                    adapter = AmenityAdapter(currentLatLng, amedata, baseContext,this)
                    amenList.adapter=adapter
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
    }
    fun filterbyAmeOther(view: View) {
        all_txt.setBackgroundResource(R.color.white)
        Bank_txt.setBackgroundResource(R.color.white)
        BeautySalon_txt.setBackgroundResource(R.color.white)
        CopyShop_txt.setBackgroundResource(R.color.white)
        ClothingStore_txt.setBackgroundResource(R.color.white)
        DentalClinic_txt.setBackgroundResource(R.color.white)
        Fuel_txt.setBackgroundResource(R.color.white)
        HairSalon_txt.setBackgroundResource(R.color.white)
        Pharmacy_txt.setBackgroundResource(R.color.white)
        GameStore_txt.setBackgroundResource(R.color.white)
        MassageSpa_txt.setBackgroundResource(R.color.white)
        PostOffice_txt.setBackgroundResource(R.color.white)
        AmeOther_txt.setBackgroundResource(R.color.secondary)
        db.collection("Amenities").whereEqualTo("Category","Other")
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    amedata.clear()
                    amedata = it.toObjects(Restaurants::class.java)
                    adapter = AmenityAdapter(currentLatLng, amedata, baseContext,this)
                    amenList.adapter=adapter
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
    }

}