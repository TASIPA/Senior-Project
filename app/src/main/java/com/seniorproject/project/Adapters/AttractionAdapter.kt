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

class AttractionAdapter(private val rssObject: MutableList<Restaurants>, private val mContext: Context, private val listener: onItemClickListener): RecyclerView.Adapter<AttractionAdapter.FeedViewHolders>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolders {

        val itemView = inflater.inflate(R.layout.card_attraction,parent,false)
        return FeedViewHolders(itemView)
    }

    private val inflater: LayoutInflater

    init{
        inflater = LayoutInflater.from(mContext)
    }

    override fun onBindViewHolder(holder: FeedViewHolders, position: Int) {
        holder.txtTitle.text = rssObject[position].Name
        holder.txtTitle1.text = rssObject[position].Location
        holder.txtTitle2.text = rssObject[position].Category
        //holder.txtTitle3.text = rssObject[position].date
        holder.txtTitle4.text = rssObject[position].distance.toString()
        holder.txtTitle5.text = rssObject[position].Rating.toString()
        var result = when (rssObject[position].imageURL) {
            "attpic1" -> R.drawable.attpic1
            "attpic2" -> R.drawable.attpic2
            "attpic3" -> R.drawable.attpic3
            "attpic4" -> R.drawable.attpic5
            else -> R.drawable.epic2
        }
        holder.img.setImageResource(result)
        // holder.imgbtn.setImageResource(R.drawable.ic_heart)


    }

    override fun getItemCount(): Int {
        return rssObject.size
    }
    inner class FeedViewHolders(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener
    {

        var txtTitle: TextView
        var txtTitle1: TextView
        var txtTitle2: TextView
        //var txtTitle3: TextView
        var txtTitle4: TextView
        var txtTitle5: TextView
        var img:ImageView
        // var imgbtn:ImageView


        //private var itemClickListener: ItemClickListener? = null

        init {

            txtTitle = itemView.findViewById(R.id.textView)
            txtTitle1 = itemView.findViewById(R.id.textView1)
            txtTitle2 = itemView.findViewById(R.id.textView2)
            //txtTitle3 = itemView.findViewById(R.id.eventDate)
            txtTitle4 = itemView.findViewById(R.id.attdistance)
            txtTitle5 = itemView.findViewById(R.id.ratetxtatt)
            img=itemView.findViewById(R.id.imageShow)
            // imgbtn=itemView.findViewById(R.id.imageButton)
            itemView.setOnClickListener(this)
            //itemView.setOnLongClickListener(this)

        }

        override fun onClick(v: View) {
            listener.onItemClick(adapterPosition)
        }



    }
}
