package com.seniorproject.project.ui.Home

import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.seniorproject.project.*
import com.seniorproject.project.Adapters.FeedAdapter
import com.seniorproject.project.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var SalayaLat = "13.800663"
    private var SalayaLong = "100.323823"
    private var API = "4282f657d89f45f71218e7bba5f90b1c"

    lateinit var auth: FirebaseAuth
    var database: FirebaseDatabase? = null
    var dbReference: DatabaseReference? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        //getProfile()

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        getProfile()
//    }

    override fun onStart() {
        super.onStart()

        weatherTask().execute()

        getProfile()

        all_cat.setOnClickListener {
            var intent= Intent(activity, Allcategories::class.java)
            startActivity(intent)
        }
        all_fea.setOnClickListener {
            var intent= Intent(activity, FeaturedActivity::class.java)
            startActivity(intent)
        }
        all_pro.setOnClickListener {
            var intent= Intent(activity, PromtionActivity::class.java)
            startActivity(intent)
        }
        res_list.setOnClickListener {
            var intent= Intent(activity, RestaurantActivity::class.java)
            startActivity(intent)
        }
        eve_list.setOnClickListener {
            var intent= Intent(activity, EventActivity::class.java)
            startActivity(intent)
        }
        ame_list.setOnClickListener {
            var intent= Intent(activity, AmenityActivity::class.java)
            startActivity(intent)
        }
        att_list.setOnClickListener {
            var intent= Intent(activity, AttractionActivity::class.java)
            startActivity(intent)
        }
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        recycler.layoutManager = linearLayoutManager
        var random= listOf<String>("Kope Hya Tai Kee","Beef 35","Shindo Ramen","O Kra Joo NimCity","Yoi-Tenki Shabu")
        var img= listOf<String>("pic7","pic2","pic1","pic10","pic6")

        val adapter = context?.let {
            FeedAdapter(
                    random,img,
                    it
            )
        }
        recycler.adapter = adapter

    }
    inner class weatherTask(): AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String? {
            var response: String?
            try {
                response = URL("https://api.openweathermap.org/data/2.5/onecall?lat=$SalayaLat&lon=$SalayaLong&units=metric&exclude=alert,minutely,&appid=$API")
                    .readText(Charsets.UTF_8)
            }
            catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                val jsonObj = JSONObject(result)
                val current = jsonObj.getJSONObject("current")
                val weather = current.getJSONArray("weather").getJSONObject(0)
                val picNow = weather.getString("icon")
                Picasso.get().load("https://openweathermap.org/img/w/$picNow.png").into(icon)
                val conditionNow = weather.getString("description")
                val dateTime = current.getLong("dt")

                val dateTimeText = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(Date(dateTime*1000))
                val tempNow = current.getString("temp")+"°C"


                val next1hr = jsonObj.getJSONArray("hourly").getJSONObject(0)
                val weather1hr = next1hr.getJSONArray("weather").getJSONObject(0)
                val pic1hr = weather1hr.getString("icon")
                Picasso.get().load("https://openweathermap.org/img/w/$pic1hr.png").into(icon1)
                val dateTime1 = next1hr.getLong("dt")
                val dateTimeText1 = SimpleDateFormat("hh a", Locale.ENGLISH).format(Date(dateTime1*1000))
                val tempNext1hr = next1hr.getString("temp")+"°C "

                val next2hr = jsonObj.getJSONArray("hourly").getJSONObject(1)
                val weather2hr = next2hr.getJSONArray("weather").getJSONObject(0)
                val pic2hr = weather2hr.getString("icon")
                Picasso.get().load("https://openweathermap.org/img/w/$pic2hr.png").into(icon2)
                val dateTime2 = next2hr.getLong("dt")
                val dateTimeText2 = SimpleDateFormat("hh a", Locale.ENGLISH).format(Date(dateTime2*1000))
                val tempNext2hr = next2hr.getString("temp")+"°C "

                val next3hr = jsonObj.getJSONArray("hourly").getJSONObject(2)
                val weather3hr = next1hr.getJSONArray("weather").getJSONObject(0)
                val pic3hr = weather1hr.getString("icon")
                Picasso.get().load("https://openweathermap.org/img/w/$pic3hr.png").into(icon3)
                val dateTime3 = next3hr.getLong("dt")
                val dateTimeText3 = SimpleDateFormat("hh a", Locale.ENGLISH).format(Date(dateTime3*1000))
                val tempNext3hr = next3hr.getString("temp")+"°C "

                val next4hr = jsonObj.getJSONArray("hourly").getJSONObject(3)
                val weather4hr = next4hr.getJSONArray("weather").getJSONObject(0)
                val pic4hr = weather4hr.getString("icon")
                Picasso.get().load("https://openweathermap.org/img/w/$pic4hr.png").into(icon4)
                val dateTime4 = next4hr.getLong("dt")
                val dateTimeText4 = SimpleDateFormat("hh a", Locale.ENGLISH).format(Date(dateTime4*1000))
                val tempNext4hr = next4hr.getString("temp")+"°C "

                val next5hr = jsonObj.getJSONArray("hourly").getJSONObject(4)
                val weather5hr = next5hr.getJSONArray("weather").getJSONObject(0)
                val pic5hr = weather5hr.getString("icon")
                Picasso.get().load("https://openweathermap.org/img/w/$pic5hr.png").into(icon5)
                val dateTime5 = next5hr.getLong("dt")
                val dateTimeText5 = SimpleDateFormat("hh a", Locale.ENGLISH).format(Date(dateTime5*1000))
                val tempNext5hr = next5hr.getString("temp")+"°C "

                tempNowShow.text = tempNow
                conditionNowShow.text = conditionNow

                next1tempShow.text = tempNext1hr
                next1timeShow.text = dateTimeText1

                next2tempShow.text = tempNext2hr
                next2timeShow.text = dateTimeText2

                next3tempShow.text = tempNext3hr
                next3timeShow.text = dateTimeText3

                next4tempShow.text = tempNext4hr
                next4timeShow.text = dateTimeText4

                next5tempShow.text = tempNext5hr
                next5timeShow.text = dateTimeText5

            }
            catch (e: Exception){

            }
        }

    }
    private fun getProfile(){

        auth= FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbReference = database?.reference!!.child("profile")

        val user = auth.currentUser
        val userref = dbReference?.child(user?.uid!!)

        userref?.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                usernameShow.text = snapshot.child("username").value.toString()
                var profilePic = snapshot.child("picurl").value.toString()

                if (profilePic!=null){
                    //profile_img.visibility = INVISIBLE
                    Picasso.get().load(profilePic).into(profile_image)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}