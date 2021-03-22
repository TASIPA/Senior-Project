//package com.seniorproject.project
//
//import android.os.AsyncTask
//import android.util.Log
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.model.BitmapDescriptorFactory
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//
//
//class GetNearbyPlacesData : AsyncTask<Any?, String?, String?>() {
//    var googlePlacesData: String? = null
//    var mMap: GoogleMap? = null
//    var url: String? = null
//
//    override fun doInBackground(vararg params: Any?): String? {
//        try {
//            Log.d("GetNearbyPlacesData", "doInBackground entered")
//            mMap = params[0] as GoogleMap
//            url = params[1] as String
//            val downloadUrl = DownloadUrl()
//            googlePlacesData = downloadUrl.readUrl(url)
//            Log.d("GooglePlacesReadTask", "doInBackground Exit")
//        } catch (e: Exception) {
//            Log.d("GooglePlacesReadTask", e.toString())
//        }
//        return googlePlacesData
//    }
//
//    override fun onPostExecute(result: String?) {
//        Log.d("GooglePlacesReadTask", "onPostExecute Entered")
//        var nearbyPlacesList: List<HashMap<String, String>>? = null
//        val dataParser = DataParser()
//        nearbyPlacesList = dataParser.parse(result)
//        ShowNearbyPlaces(nearbyPlacesList)
//        Log.d("GooglePlacesReadTask", "onPostExecute Exit")
//    }
//
//    private fun ShowNearbyPlaces(nearbyPlacesList: List<HashMap<String, String>>?) {
//        for (i in nearbyPlacesList!!.indices) {
//            Log.d("onPostExecute", "Entered into showing locations")
//            val markerOptions = MarkerOptions()
//            val googlePlace = nearbyPlacesList[i]
//            val lat = googlePlace["lat"]!!.toDouble()
//            val lng = googlePlace["lng"]!!.toDouble()
//            val placeName = googlePlace["place_name"]
//            val vicinity = googlePlace["vicinity"]
//            val latLng = LatLng(lat, lng)
//            markerOptions.position(latLng)
//            markerOptions.title("$placeName : $vicinity")
//            mMap!!.addMarker(markerOptions)
//            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//            //move map camera
//            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))
//        }
//    }
//}