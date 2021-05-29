package com.seniorproject.project.Adapters

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.provider.Settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.seniorproject.project.Interface.onItemClickListener
import com.seniorproject.project.Interface.onItemClickListener1
import com.seniorproject.project.R
import com.seniorproject.project.models.Events
import com.seniorproject.project.models.Restaurants

private lateinit var locationManager: LocationManager
private lateinit var locationListener: LocationListener
private lateinit var currentLatLng: LatLng

class EventAdapter(private val rssObject: MutableList<Events>, private val mContext: Context,private val listener: onItemClickListener1): RecyclerView.Adapter<EventAdapter.FeedViewHolders>()
{   private var filteredData=rssObject
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolders {

        val itemView = inflater.inflate(R.layout.card_event,parent,false)
        return FeedViewHolders(itemView)
    }

    private val inflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onBindViewHolder(holder: FeedViewHolders, position: Int) {

        holder.txtTitle.text = filteredData[position].Name
        holder.txtTitle1.text = filteredData[position].Location
        //holder.txtTitle2.text = rssObject[position].category
        holder.txtTitle3.text =filteredData[position].Date
        holder.txtTitle4.text = filteredData[position].distance.toString()
        var evelat = filteredData[position].Latitude.toString()
        var evelong = filteredData[position].Longitude.toString()

        val loc1 = Location("")
//        loc1.setLatitude(currentLatLon.latitude)
//        loc1.setLongitude(currentLatLon.longitude)

        val loc2 = Location("")
        loc2.setLatitude(evelat.toDouble())
        loc2.setLongitude(evelong.toDouble())

        val distanceInMeters: Float = loc1.distanceTo(loc2)
        var distanceInKm = String.format("%.2f", (distanceInMeters / 1000)).toFloat()

        //holder.txtTitle4.text = distanceInKm.toString() + "km"

        var result = when (filteredData[position].imageURL) {
            "epic1" -> R.drawable.epic1
            "epic2" -> R.drawable.epic2
            "epic3" -> R.drawable.epic3
            "epic4" -> R.drawable.epic4
            else -> R.drawable.epic5
        }
        holder.img.setImageResource(result)
       // holder.imgbtn.setImageResource(R.drawable.ic_heart)


    }

    override fun getItemCount(): Int {
        return filteredData.size
    }
    inner class FeedViewHolders(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener
    {

        var txtTitle: TextView
        var txtTitle1: TextView
       // var txtTitle2: TextView
        var txtTitle3: TextView
        var txtTitle4: TextView
        var img:ImageView
       // var imgbtn:ImageView


        //private var itemClickListener: ItemClickListener? = null

        init {

            txtTitle = itemView.findViewById(R.id.textView)
            txtTitle1 = itemView.findViewById(R.id.textView1)
            //txtTitle2 = itemView.findViewById(R.id.eventCategory)
            txtTitle3 = itemView.findViewById(R.id.eventDate)
            txtTitle4 = itemView.findViewById(R.id.eventDistance)
            img=itemView.findViewById(R.id.imageShow)
           // imgbtn=itemView.findViewById(R.id.imageButton)
            itemView.setOnClickListener(this)
            //itemView.setOnLongClickListener(this)

        }

        override fun onClick(v: View) {
           listener.onItemClick(adapterPosition,filteredData)
        }



    }
    fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var st=constraint.toString()
                if (st.isEmpty()){
                    filteredData=rssObject
                }
                else{
                    var lst= mutableListOf<Events>()
                    for (row in rssObject){
                        if (row.Name.toLowerCase().contains(st.toLowerCase()))
                            lst.add(row)
                    }
                    filteredData=lst
                }

                var filterResults= FilterResults()
                filterResults.values=filteredData
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredData=results!!.values as MutableList<Events>
                notifyDataSetChanged()
            }
        }

    }
    /*fun getUserLocation(){
        currentLatLng = LatLng(0.0,0.0)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                if (location == null){
                    //Toast.makeText(applicationContext, "Location Not Found",Toast.LENGTH_SHORT).show()
                }
                else{
                    currentLatLng = LatLng(location.latitude,location.longitude)

                }
            }

//            override fun onProviderDisabled(provider: String) {
//                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                startActivity(intent)
//            }
        }
        requestLocation()
    }

    private fun requestLocation() {
        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),10)
            }
            return
        }
        locationManager.requestLocationUpdates("gps",5000,0f,locationListener)
//        gpsBtn.setOnClickListener{
//            if (currentLatLng!=null){
//                latText.setText(currentLatLng.latitude.toString())
//                longText.setText(currentLatLng.longitude.toString())
//            }
//        }
    }

    *//**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *//*

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode){
            10 -> requestLocation()
            else -> Toast.makeText(mContext,"Do not nothing (becuz the requestCode != 10)", Toast.LENGTH_SHORT).show()
        }
    }*/
}
