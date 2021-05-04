package com.seniorproject.project.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.seniorproject.project.Profile
import com.seniorproject.project.R
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        return root
    }

    override fun onStart() {
        super.onStart()
        profile.setOnClickListener {
            var intent= Intent(activity, Profile::class.java)
            startActivity(intent)
        }
    }

}