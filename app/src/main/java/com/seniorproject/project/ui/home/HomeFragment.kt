package com.seniorproject.project.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.seniorproject.project.Adapters.FeedAdapter
import com.seniorproject.project.AmenityActivity
import com.seniorproject.project.EventActivity
import com.seniorproject.project.R
import com.seniorproject.project.RestaurantActivity
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
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        resrec.layoutManager = linearLayoutManager
        var random= listOf<String>("Hello","World","This","is","test","list")
        val adapter = context?.let {
            FeedAdapter(
                    random,
                    it
            )
        }
        resrec.adapter = adapter
        resact.setOnClickListener {
            var intent= Intent(activity,RestaurantActivity::class.java)
            startActivity(intent)
        }
        val linearLayoutManager1 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        everec.layoutManager = linearLayoutManager1
        val adapter1 = context?.let {
            FeedAdapter(
                    random,
                    it
            )
        }


        everec.adapter=adapter1
        eveact.setOnClickListener {
            var intent= Intent(activity,EventActivity::class.java)
            startActivity(intent)
        }

        val linearLayoutManager2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        recamen.layoutManager = linearLayoutManager2
        val adapter2 = context?.let {
            FeedAdapter(
                    random,
                    it
            )
        }

        recamen.adapter=adapter2
        amenact.setOnClickListener {
            var intent= Intent(activity,AmenityActivity::class.java)
            startActivity(intent)
        }

    }
}