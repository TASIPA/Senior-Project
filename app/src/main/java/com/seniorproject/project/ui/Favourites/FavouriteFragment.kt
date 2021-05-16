package com.seniorproject.project.ui.Favourites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.seniorproject.project.Adapters.FavoriteAdapter
import com.seniorproject.project.R
import com.seniorproject.project.models.FavObj
import com.seniorproject.project.models.Favorite

import kotlinx.android.synthetic.main.fragment_favorite.*

class FavouriteFragment : Fragment() {

    var data: ArrayList<Favorite>? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_favorite, container, false)
        return root
    }

    override fun onStart() {
        super.onStart()
        data=FavObj.getData()
        if (data!=null){
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        favList.layoutManager = linearLayoutManager
        val adapter = context?.let {
            FavoriteAdapter(
                data!!,
                it
            )
        }
        favList.adapter=adapter
    }
        else{
            favList.visibility= INVISIBLE
        }
    }

}