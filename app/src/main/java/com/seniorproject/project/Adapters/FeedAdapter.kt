package com.seniorproject.project.Adapters

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seniorproject.project.R

class FeedAdapter(private val rssObject: List<String>,private val rssImg: List<String>, private val mContext: Context): RecyclerView.Adapter<FeedAdapter.FeedViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {

        val itemView = inflater.inflate(R.layout.home_card,parent,false)
        return FeedViewHolder(itemView)
    }

    private val inflater: LayoutInflater

    init{
        inflater = LayoutInflater.from(mContext)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.txtTitle.text = rssObject[position].toString()
        var result = when (rssImg[position]) {
            "apic1" -> R.drawable.apic1
            "apic2" -> R.drawable.apic2
            "apic3" -> R.drawable.apic3
            "apic4" -> R.drawable.apic4
            "apic5"-> R.drawable.apic5
            "epic1" -> R.drawable.epic1
            "epic2" -> R.drawable.epic2
            "epic3" -> R.drawable.epic3
            "epic4" -> R.drawable.epic4
            "epic5"-> R.drawable.epic5
            "pic1" -> R.drawable.pic1
            "pic2" -> R.drawable.pic2
            "pic6" -> R.drawable.pic6
            "pic7" -> R.drawable.pic7
            else -> R.drawable.pic10
        }
        holder.img.setImageResource(result)

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
    class FeedViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener,View.OnLongClickListener
    {

        var txtTitle: TextView
        lateinit var img:ImageView

        //private var itemClickListener: ItemClickListener? = null

        init {

            txtTitle = itemView.findViewById(R.id.cardText)
            img=itemView.findViewById(R.id.cardImage)

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
