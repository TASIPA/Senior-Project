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
        var random= listOf<String>("Kope Hya Tai Kee","Beef 35","Shindo Ramen","O Kra Joo NimCity","Yoi-Tenki Shabu")
        var img= listOf<String>("pic7","pic2","pic1","pic10","pic6")

        var random1= listOf<String>("Central Plaza Salaya","MUIC X-Mas ","Makro Sale","Motor Expo","Bangkok furniture fair 2020 ")
        var img1= listOf<String>("epic1","epic2","epic3","epic4","epic5")
        var random2= listOf<String>("PTT Gas Station","Kasikorn Bank","KrungThai Bank","Bangkok Bank","7-eleven")
        var img2= listOf<String>("apic1","apic2","apic3","apic4","apic5")
        val adapter = context?.let {
            FeedAdapter(
                    random,img,
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
                    random1,img1,
                    it
            )
        }


        everec.adapter=adapter1
        eveact.setOnClickListener {
            var intent= Intent(activity,EventActivity::class.java)
            startActivity(intent)
        }

        val linearLayoutManager2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        recame.layoutManager = linearLayoutManager2
        val adapter2 = context?.let {
            FeedAdapter(
                    random2,img2,
                    it
            )
        }

        recame.adapter=adapter2
        ameact.setOnClickListener {
            var intent= Intent(activity,AmenityActivity::class.java)
            startActivity(intent)
        }

    }
}