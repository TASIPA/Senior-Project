package com.seniorproject.project.ui.Favourites

import android.content.Intent
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
import com.seniorproject.project.*
import com.seniorproject.project.Adapters.FavoriteAdapter
import com.seniorproject.project.Interface.onItemClickListener
import com.seniorproject.project.R
import com.seniorproject.project.models.Favorite
import kotlinx.android.synthetic.main.fragment_favorite.*


class FavouriteFragment : Fragment(),onItemClickListener {

    var data: ArrayList<Favorite>? = ArrayList()
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

    override fun onResume() {
        super.onResume()
        reference!!.get().addOnSuccessListener {
            data?.clear()
            it.children?.forEach { i ->
                var name = it.child(i.key.toString()).child("name").value.toString()
                var pic = it.child(i.key.toString()).child("pic").value.toString()
                var type = it.child(i.key.toString()).child("type").value.toString()
                var rating =
                    it.child(i.key.toString()).child("rating").value.toString().toDouble()
                var distance =
                    it.child(i.key.toString()).child("distance").value.toString()
                        .toDouble()
                var id =
                    it.child(i.key.toString()).child("id").value.toString()
                data?.add(Favorite(name, pic, type, rating, distance, id))
            }
            if (data != null) {
                val linearLayoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                favList.layoutManager = linearLayoutManager
                var adapter1 = context?.let {
                    FavoriteAdapter(
                        data!!,
                        it, this
                    )
                }!!

                favList.adapter = adapter1
            } else {
                favList.visibility= INVISIBLE
            }

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    override fun onItemClick(position: Int) {
       var intent:Intent=when(data!![position].id){
           "Restaurant" -> Intent(activity, ResDetailActivity::class.java)
           "Amenity" -> Intent(activity, AmeDetailActivity::class.java)
           else -> Intent(activity, AttDetailActivity::class.java)
       }
        intent.putExtra("name", data!![position].name)
        intent.putExtra("type", data!![position].type)
        intent.putExtra("image", data!![position].pic)
        intent.putExtra("rating", data!![position].rating.toString())
        startActivity(intent)
    }

}