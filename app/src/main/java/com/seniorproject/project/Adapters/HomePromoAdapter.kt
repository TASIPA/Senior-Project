package com.seniorproject.project.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seniorproject.project.Interface.onItemClickListener
import com.seniorproject.project.Interface.onItemClickListener2
import com.seniorproject.project.R
import com.seniorproject.project.models.Promotions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class HomePromoAdapter(private val rssObject: MutableList<Promotions>, private val mContext: Context, private val listener2: onItemClickListener2): RecyclerView.Adapter<HomePromoAdapter.HomePromotionViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePromotionViewHolder {

        val itemView = inflater.inflate(R.layout.card_home,parent,false)
        return HomePromotionViewHolder(itemView)
    }


    private val inflater: LayoutInflater

    init{
        inflater = LayoutInflater.from(mContext)
    }

    override fun onBindViewHolder(holder: HomePromotionViewHolder, position: Int) {
        holder.redText.text="-"+rssObject[position].Discount
        Picasso.get().load(rssObject[position].imageURL).into(holder.prom_img)
        //holder.txtTitle.text = rssObject[position].toString()

        //holder.img.setImageResource(result)

    }
    override fun getItemCount(): Int {
        return rssObject.size
    }

    inner class HomePromotionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener,
        View.OnLongClickListener
    {

        var redText: TextView
        var red: ImageView
        var prom_img: ImageView
//        var desFlag: ImageView
//        var depFlag: ImageView


        init {

            prom_img=itemView.findViewById(R.id.prom_img)
            red=itemView.findViewById(R.id.red)
            redText=itemView.findViewById(R.id.redText)

            //itemView.setOnClickListener(this)
            //itemView.setOnLongClickListener(this)

        }

        override fun onClick(v: View) {
            listener2.onItemClick(adapterPosition,rssObject)
        }

        override fun onLongClick(v: View): Boolean {
            //itemClickListener!!.onClick(v,adapterPosition,true)
            return true
        }

    }

}