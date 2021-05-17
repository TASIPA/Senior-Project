package com.seniorproject.project.ui.Favourites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE

import android.view.ViewGroup

import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.seniorproject.project.Adapters.FavoriteAdapter
import com.seniorproject.project.R
import com.seniorproject.project.models.Favorite

import kotlinx.android.synthetic.main.fragment_favorite.*

class FavouriteFragment : Fragment() {

    var data: ArrayList<Favorite>? = ArrayList<Favorite>()
    var rootNode: FirebaseDatabase? = null
    var user: FirebaseAuth? = null
    var reference: DatabaseReference? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_favorite, container, false)
        rootNode = FirebaseDatabase.getInstance()
        user = FirebaseAuth.getInstance()
        var currentuser = user!!.currentUser?.uid
        reference = rootNode!!.getReference("favorite").child(currentuser!!)
        return root
    }

    override fun onStart() {
        super.onStart()

//        reference?.addValueEventListener(object : ValueEventListener {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                snapshot.children?.forEach { i ->
//
//                    Log.d(
//                        "firebase",
//                        "${snapshot.child(i.key.toString()).child("name").value.toString()}"
//                    )
//                    var name = snapshot.child(i.key.toString()).child("name").value.toString()
//                    var pic = snapshot.child(i.key.toString()).child("pic").value.toString()
//                    var type = snapshot.child(i.key.toString()).child("type").value.toString()
//                    var rating =
//                        snapshot.child(i.key.toString()).child("rating").value.toString().toDouble()
//                    var distance =
//                        snapshot.child(i.key.toString()).child("distance").value.toString()
//                            .toDouble()
//                    data?.add(Favorite(name, pic, type, rating, distance))
//                }
//                //Log.d("firebase","${snapshot.value}")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
        reference!!.get().addOnSuccessListener {
            it.children?.forEach { i ->
//                Log.d(
//                    "firebase",
//                    "${it.child(i.key.toString()).child("name").value.toString()}"
//                )
                var name = it.child(i.key.toString()).child("name").value.toString()
                var pic = it.child(i.key.toString()).child("pic").value.toString()
                var type = it.child(i.key.toString()).child("type").value.toString()
                var rating =
                    it.child(i.key.toString()).child("rating").value.toString().toDouble()
                var distance =
                    it.child(i.key.toString()).child("distance").value.toString()
                        .toDouble()
                data?.add(Favorite(name, pic, type, rating, distance))
//                Log.d(
//                    "firebase",
//                    "${data?.get(0)?.name}"
//                )
            }
            if (data != null) {
                val linearLayoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                favList.layoutManager = linearLayoutManager
                val adapter = context?.let {
                    FavoriteAdapter(
                        data!!,
                        it
                    )
                }
                favList.adapter = adapter
            } else {
                favList.visibility= INVISIBLE
            }

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

    }


}