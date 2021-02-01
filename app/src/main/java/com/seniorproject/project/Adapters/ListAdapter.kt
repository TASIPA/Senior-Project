package com.seniorproject.project.Adapters

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seniorproject.project.R

class ListAdapter(private val rssObject: List<String>, private val mContext: Context): RecyclerView.Adapter<FeedViewHolders>()
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
        //holder.txtTitle.text = rssObject[position]
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
}
class FeedViewHolders(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener,View.OnLongClickListener
{

    var txtTitle: TextView
    var txtTitle1: TextView
    var txtTitle2: TextView
    var img:ImageView


    //private var itemClickListener: ItemClickListener? = null

    init {

        txtTitle = itemView.findViewById(R.id.textView)
        txtTitle1 = itemView.findViewById(R.id.textView1)
        txtTitle2 = itemView.findViewById(R.id.dis)
        img=itemView.findViewById(R.id.imageShow)



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