package com.seniorproject.project.ui.Home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.seniorproject.project.*
import com.seniorproject.project.Adapters.FeedAdapter
import kotlinx.android.synthetic.main.activity_attraction.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onStart() {
        super.onStart()
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
}