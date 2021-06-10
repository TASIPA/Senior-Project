package com.seniorproject.project.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seniorproject.project.R
import com.seniorproject.project.models.Promotions

class PromoAdapter(private val rssObject: MutableList<Promotions>, private val mContext: Context): RecyclerView.Adapter<PromoAdapter.PromotionViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromotionViewHolder {

        val itemView = inflater.inflate(R.layout.card_promotion,parent,false)
        return PromotionViewHolder(itemView)
    }


    private val inflater: LayoutInflater

    init{
        inflater = LayoutInflater.from(mContext)
    }

    override fun onBindViewHolder(holder: PromotionViewHolder, position: Int) {
        holder.Category.text=rssObject[position].Category
        //holder.Discont.text=rssObject[position].Discount
        holder.ProdName.text=rssObject[position].ProductName
        holder.ShopName.text=rssObject[position].ShopName
        holder.Valid.text=rssObject[position].ValidTo

        //holder.txtTitle.text = rssObject[position].toString()

        //holder.img.setImageResource(result)

    }
    override fun getItemCount(): Int {
        return rssObject.size
    }
    class PromotionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener,
        View.OnLongClickListener
    {

        var Category: TextView
        //var Discont: TextView
        var ProdName: TextView
        var ShopName: TextView
        var Valid: TextView

        var image: ImageView
//        var desFlag: ImageView
//        var depFlag: ImageView


        init {

            Category = itemView.findViewById(R.id.categoryShow)
            //Discont = itemView.findViewById(R.id.traindetail)
            ProdName= itemView.findViewById(R.id.productNameShow)
            ShopName = itemView.findViewById(R.id.shopNameShow)
            Valid= itemView.findViewById(R.id.promoTimeShow)

            image=itemView.findViewById(R.id.shopPic)

            //itemView.setOnClickListener(this)
            //itemView.setOnLongClickListener(this)

        }

        override fun onClick(v: View) {
            //itemClickListener!!.onClick(v, adapterPosition,false)
        }

        override fun onLongClick(v: View): Boolean {
            //itemClickListener!!.onClick(v,adapterPosition,true)
            return true
        }

    }

}