package com.seniorproject.project.models

import android.util.Log

object FavObj{
    private val data=ArrayList<Favorite>()
    fun getData(): ArrayList<Favorite>{
        return data
    }
    fun addData(name: String, pic:String,  type: String, rating: Double=4.5, distance:Double=0.0){
        data.add(Favorite(name,pic,type,rating,distance))
        printData()
    }

    private fun printData() {
        Log.d("Working", data[0].name)
    }

}