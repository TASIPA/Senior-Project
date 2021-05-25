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
import com.seniorproject.project.models.Restaurants

class AmenityAdapter(private val rssObject: MutableList<Restaurants>, private val mContext: Context, private val listener: onItemClickListener): RecyclerView.Adapter<AmenityAdapter.FeedViewHolders>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolders {

        val itemView = inflater.inflate(R.layout.card_amenities,parent,false)
        return FeedViewHolders(itemView)
    }

    private val inflater: LayoutInflater

    init{
        inflater = LayoutInflater.from(mContext)
    }

    override fun onBindViewHolder(holder: FeedViewHolders, position: Int) {
        holder.txtTitle.text = rssObject[position].Name
        holder.txtTitle1.text = rssObject[position].Category
        holder.txtTitle2.text = rssObject[position].distance.toString()
        holder.txtTitle3.text = rssObject[position].Rating.toString()
        var result = when (rssObject[position].imageURL) {
            "apic1" -> R.drawable.apic1
            "apic2" -> R.drawable.apic2
            "apic3" -> R.drawable.apic3
            "apic4" -> R.drawable.apic4
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

        var txtTitle: TextView = itemView.findViewById(R.id.textView)
        var txtTitle1: TextView = itemView.findViewById(R.id.textView1)
        var txtTitle2: TextView = itemView.findViewById(R.id.resDistance)
        var txtTitle3: TextView = itemView.findViewById(R.id.vv12)
        var img:ImageView = itemView.findViewById(R.id.imageShow)
        // var imgbtn:ImageView


        init {

            //            imgbtn=itemView.findViewById(R.id.imageButton)



            itemView.setOnClickListener(this)
            //itemView.setOnLongClickListener(this)

        }

        override fun onClick(v: View) {
            listener.onItemClick(adapterPosition)
        }



    }
}
