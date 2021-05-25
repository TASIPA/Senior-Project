package com.seniorproject.project.Adapters

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seniorproject.project.Interface.onItemClickListener
import com.seniorproject.project.R
import com.seniorproject.project.models.Favorite
import com.seniorproject.project.models.Restaurants


class FavoriteAdapter(private val rssObject: List<Restaurants>, private val mContext: Context,private val listener: onItemClickListener): RecyclerView.Adapter<FavoriteAdapter.FeedViewHolders>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolders {

        val itemView = inflater.inflate(R.layout.card_restaurant,parent,false)
        return FeedViewHolders(itemView)
    }

    private val inflater: LayoutInflater

    init{
        inflater = LayoutInflater.from(mContext)
    }

    override fun onBindViewHolder(holder: FeedViewHolders, position: Int) {
        holder.txtTitle.text = rssObject[position].Name
        holder.txtTitle1.text = rssObject[position].Category
        holder.txtTitle2.text = rssObject[position].Rating.toString()
        // holder.rate.rating = rssObject[position].rating.toFloat()
        var result = when (rssObject[position].imageURL) {
            "apic1" -> R.drawable.apic1
            "apic2" -> R.drawable.apic2
            "apic3" -> R.drawable.apic3
            "apic4" -> R.drawable.apic4
            "apic5"-> R.drawable.apic5
            "attpic1" -> R.drawable.attpic1
            "attpic2" -> R.drawable.attpic2
            "attpic3" -> R.drawable.attpic3
            "attpic4" -> R.drawable.attpic5
            "attpic5"-> R.drawable.epic2
            "pic1" -> R.drawable.pic1
            "pic2" -> R.drawable.pic2
            "pic6" -> R.drawable.pic6
            "pic7" -> R.drawable.pic7
            else -> R.drawable.epic2
        }
        holder.img.setImageResource(result)
        //holder.imgbtn.setImageResource(R.drawable.ic_heart)


    }

    override fun getItemCount(): Int {
        return rssObject.size
    }
    inner class FeedViewHolders(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener
    {

        var txtTitle: TextView
        var txtTitle1: TextView
        var txtTitle2: TextView
        //  var rate: RatingBar
        var img:ImageView


        init {

            txtTitle = itemView.findViewById(R.id.textView)
            txtTitle1 = itemView.findViewById(R.id.textView1)
            txtTitle2 = itemView.findViewById(R.id.vv12)
            //  rate= itemView.findViewById(R.id.ratingBar2)
            img=itemView.findViewById(R.id.imageShow)
            //  imgbtn=itemView.findViewById(R.id.imageButton)

            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            listener.onItemClick(adapterPosition)
        }

    }
}
