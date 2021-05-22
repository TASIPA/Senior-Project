package com.seniorproject.project.models

import java.io.Serializable

class Restaurants(
    val id: Int, val Name: String,
    val imageURL:String, val Category: String, val Rating: Double,
    var Description:String, val distance:Double,
    val Latitude:Double,
    val Longitude:Double,
    val Telephone: String
): Serializable {
    constructor(): this(0,"","","",0.0,"",0.0,0.0,0.0,"012-9999-1000")
}