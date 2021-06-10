package com.seniorproject.project.models

public class Promotions (val Category: String, val Discount: String, val Latitude: Double, val Longitude: Double, val ProductName:String,
                         val ShopName: String, val ValidTo: String, distance: Int, id: Int, imageURL: String){
    constructor(): this("","",0.0,0.0,"","","",0,0,"")
}