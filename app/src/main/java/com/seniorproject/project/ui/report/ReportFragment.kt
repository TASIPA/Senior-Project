package com.seniorproject.project.ui.report

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
import com.seniorproject.project.Adapters.RailwayAdapter
import com.seniorproject.project.R
import com.seniorproject.project.WebActivity
import com.seniorproject.project.models.RailwayData
import kotlinx.android.synthetic.main.report_fragment.*

class ReportFragment : Fragment() {

    private lateinit var reportViewModel: ReportViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reportViewModel =
            ViewModelProvider(this).get(ReportViewModel::class.java)
        val root = inflater.inflate(R.layout.report_fragment, container, false)
        return root
    }

    override fun onStart() {
        super.onStart()
         webBTN.setOnClickListener {
           val intent = Intent(activity, WebActivity::class.java)//back to the login page
           startActivity(intent)
       }
       val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
       RailList.layoutManager = linearLayoutManager
       var data= listOf<RailwayData>(RailwayData("356","Ordinary","Salaya","07:00 am","Bangkok","08:05 am"),
                       RailwayData("84","Special Express","Salaya","07:34 am","Bangkok","08:20 am"),
                       RailwayData("172","Rapid","Salaya","08:14 am","Bangkok","08:50 am"),
                       RailwayData("38","Special Express","Salaya","09:10 am","Bangkok","10:10 am"),
                       RailwayData("261","Ordinary","Salaya","10:15 am","Hua Hin","01:35 pm"),
                       RailwayData("251","Ordinary","Salaya","01:33 pm","Phrachuchap Khiri Khan","05:20 pm")
                       )
       val adapter = context?.let { RailwayAdapter(data, it) }

       RailList.adapter = adapter
    }
}