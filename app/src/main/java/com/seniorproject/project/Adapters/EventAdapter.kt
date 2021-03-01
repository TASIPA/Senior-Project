package com.seniorproject.project.Adapters

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seniorproject.project.R
import com.seniorproject.project.models.Events

class EventAdapter(private val rssObject: List<Events>, private val mContext: Context): RecyclerView.Adapter<EventAdapter.FeedViewHolders>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolders {

        val itemView = inflater.inflate(R.layout.card_event,parent,false)
        return FeedViewHolders(itemView)
    }

    private val inflater: LayoutInflater

    init{
        inflater = LayoutInflater.from(mContext)
    }

    override fun onBindViewHolder(holder: FeedViewHolders, position: Int) {
        holder.txtTitle.text = rssObject[position].name
        holder.txtTitle1.text = rssObject[position].location
        holder.txtTitle2.text = rssObject[position].category
        holder.txtTitle3.text = rssObject[position].date
        holder.txtTitle4.text = rssObject[position].distance.toString()
        var result = when (rssObject[position].pic) {
            "epic1" -> R.drawable.epic1
            "epic2" -> R.drawable.epic2
            "epic3" -> R.drawable.epic3
            "epic4" -> R.drawable.epic4
            else -> R.drawable.epic5
        }
        holder.img.setImageResource(result)
        holder.imgbtn.setImageResource(R.drawable.heart1)


    }
    /*holder.setItemClickListener(object :
        ItemClickListener {
        override fun onClick(view: View, position: Int, isLongClick: Boolean) {

            if (!isLongClick) {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.items[position].link))
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(browserIntent)

            }
        }
    })
}*/
    override fun getItemCount(): Int {
        return rssObject.size
    }
    class FeedViewHolders(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener,View.OnLongClickListener
    {

        var txtTitle: TextView
        var txtTitle1: TextView
        var txtTitle2: TextView
        var txtTitle3: TextView
        var txtTitle4: TextView
        var img:ImageView
        var imgbtn:ImageView


        //private var itemClickListener: ItemClickListener? = null

        init {

            txtTitle = itemView.findViewById(R.id.textView)
            txtTitle1 = itemView.findViewById(R.id.textView1)
            txtTitle2 = itemView.findViewById(R.id.eventCategory)
            txtTitle3 = itemView.findViewById(R.id.eventDate)
            txtTitle4 = itemView.findViewById(R.id.eventDistance)
            img=itemView.findViewById(R.id.imageShow)
            imgbtn=itemView.findViewById(R.id.imageButton)



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
