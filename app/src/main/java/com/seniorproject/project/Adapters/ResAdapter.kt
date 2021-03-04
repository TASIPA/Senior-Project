package com.seniorproject.project.Adapters

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seniorproject.project.Interface.onItemClickListener
import com.seniorproject.project.R
import com.seniorproject.project.models.Restaurants

class ResAdapter(private val rssObject: List<Restaurants>, private val mContext: Context, private val listener: onItemClickListener): RecyclerView.Adapter<ResAdapter.FeedViewHolders>()
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
        holder.txtTitle.text = rssObject[position].name
        holder.txtTitle1.text = rssObject[position].type
        holder.txtTitle2.text = rssObject[position].rating.toString()
        holder.rate.rating = rssObject[position].rating.toFloat()
        var result = when (rssObject[position].pic) {
            "pic1" -> R.drawable.pic1
            "pic2" -> R.drawable.pic2
            "pic6" -> R.drawable.pic6
            "pic7" -> R.drawable.pic7
            else -> R.drawable.pic10
        }
        holder.img.setImageResource(result)
        holder.imgbtn.setImageResource(R.drawable.ic_heart)


    }

    override fun getItemCount(): Int {
        return rssObject.size
    }
    inner class FeedViewHolders(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener
    {

        var txtTitle: TextView
        var txtTitle1: TextView
        var txtTitle2: TextView
        var rate: RatingBar
        var img:ImageView
        var imgbtn:ImageView


        //private var itemClickListener: ItemClickListener? = null

        init {

            txtTitle = itemView.findViewById(R.id.textView)
            txtTitle1 = itemView.findViewById(R.id.textView1)
            txtTitle2 = itemView.findViewById(R.id.vv12)
            rate= itemView.findViewById(R.id.ratingBar2)
            img=itemView.findViewById(R.id.imageShow)
            imgbtn=itemView.findViewById(R.id.imageButton)



            itemView.setOnClickListener(this)
            //itemView.setOnLongClickListener(this)

        }

        override fun onClick(v: View) {
            listener.onItemClick(adapterPosition)
        }



    }
}
