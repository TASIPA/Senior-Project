package com.seniorproject.project.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seniorproject.project.Interface.onItemClickListener2
import com.seniorproject.project.R
import com.seniorproject.project.models.Advertisements
import com.seniorproject.project.models.Promotions
import com.squareup.picasso.Picasso

class AdverAdapter (private val rssObject: MutableList<Advertisements>, private val mContext: Context): RecyclerView.Adapter<AdverAdapter.AdverViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdverViewHolder {

        val itemView = inflater.inflate(R.layout.card_adver,parent,false)
        return AdverViewHolder(itemView)
    }

    private val inflater: LayoutInflater

    init{
        inflater = LayoutInflater.from(mContext)
    }

    override fun onBindViewHolder(holder: AdverViewHolder, position: Int) {

        Picasso.get().load(rssObject[position].Promotion_IMG).into(holder.prom_img2)
        //holder.txtTitle.text = rssObject[position].toString()
        Log.d("Painty","Hello!!")
        //holder.img.setImageResource(result)

    }
    override fun getItemCount(): Int {
        return rssObject.size
    }

    inner class AdverViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {

        var prom_img2: ImageView
//        var desFlag: ImageView
//        var depFlag: ImageView


        init {

            prom_img2=itemView.findViewById(R.id.prom_img2)

        }

    }

}