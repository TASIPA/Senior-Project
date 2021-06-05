package com.seniorproject.project.models

class RailwayData (val TrainNo: Int, val Type: String, val DepStation: String, val DepTime:String,
                    val DestStation: String,val DestTime: String){
    constructor(): this(0,"","","","","")
}