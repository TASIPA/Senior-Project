package com.seniorproject.project.Interface

import com.seniorproject.project.models.Events
import com.seniorproject.project.models.Restaurants

interface onItemClickListener {
    fun onItemClick(position:Int,data:MutableList<Restaurants>)
}
interface onItemClickListener1 {
    fun onItemClick(position:Int,data:MutableList<Events>)
}