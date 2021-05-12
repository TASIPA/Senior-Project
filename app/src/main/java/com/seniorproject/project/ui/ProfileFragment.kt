package com.seniorproject.project.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.seniorproject.project.Allcategories
import com.seniorproject.project.LoginActivity

import com.seniorproject.project.Profile
import com.seniorproject.project.R
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.profile


class ProfileFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    var database: FirebaseDatabase? = null
    var dbReference: DatabaseReference? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth= FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbReference = database?.reference!!.child("profile")

        getProfile()

    }

    override fun onStart() {
        super.onStart()

        profile.setOnClickListener {
            var intent= Intent(activity, Profile::class.java)
            startActivity(intent)
        }

        signoutBtn.setOnClickListener {
            auth.signOut()
            var intent= Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    private fun getProfile(){

        val user = auth.currentUser
        val userref = dbReference?.child(user?.uid!!)

        userref?.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                profile_name.text = snapshot.child("name").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}