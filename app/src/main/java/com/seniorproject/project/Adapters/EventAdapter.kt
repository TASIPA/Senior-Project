package com.seniorproject.project.Adapters

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seniorproject.project.Interface.onItemClickListener
import com.seniorproject.project.Interface.onItemClickListener1
import com.seniorproject.project.R
import com.seniorproject.project.models.Events
import com.seniorproject.project.models.Restaurants

class EventAdapter(private val rssObject: MutableList<Events>, private val mContext: Context,private val listener: onItemClickListener1): RecyclerView.Adapter<EventAdapter.FeedViewHolders>()
{   private var filteredData=rssObject
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolders {

        val itemView = inflater.inflate(R.layout.card_event,parent,false)
        return FeedViewHolders(itemView)
    }

    private val inflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onBindViewHolder(holder: FeedViewHolders, position: Int) {
        holder.txtTitle.text = filteredData[position].Name
        holder.txtTitle1.text = filteredData[position].Location
        //holder.txtTitle2.text = rssObject[position].category
        holder.txtTitle3.text =filteredData[position].Date
        holder.txtTitle4.text = filteredData[position].distance.toString()
        var result = when (filteredData[position].imageURL) {
            "epic1" -> R.drawable.epic1
            "epic2" -> R.drawable.epic2
            "epic3" -> R.drawable.epic3
            "epic4" -> R.drawable.epic4
            else -> R.drawable.epic5
        }
        holder.img.setImageResource(result)
       // holder.imgbtn.setImageResource(R.drawable.ic_heart)


    }

    override fun getItemCount(): Int {
        return filteredData.size
    }
    inner class FeedViewHolders(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener
    {

        var txtTitle: TextView
        var txtTitle1: TextView
       // var txtTitle2: TextView
        var txtTitle3: TextView
        var txtTitle4: TextView
        var img:ImageView
       // var imgbtn:ImageView


        //private var itemClickListener: ItemClickListener? = null

        init {

            txtTitle = itemView.findViewById(R.id.textView)
            txtTitle1 = itemView.findViewById(R.id.textView1)
            //txtTitle2 = itemView.findViewById(R.id.eventCategory)
            txtTitle3 = itemView.findViewById(R.id.eventDate)
            txtTitle4 = itemView.findViewById(R.id.eventDistance)
            img=itemView.findViewById(R.id.imageShow)
           // imgbtn=itemView.findViewById(R.id.imageButton)
            itemView.setOnClickListener(this)
            //itemView.setOnLongClickListener(this)

        }

        override fun onClick(v: View) {
           listener.onItemClick(adapterPosition,filteredData)
        }



    }
    fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var st=constraint.toString()
                if (st.isEmpty()){
                    filteredData=rssObject
                }
                else{
                    var lst= mutableListOf<Events>()
                    for (row in rssObject){
                        if (row.Name.toLowerCase().contains(st.toLowerCase()))
                            lst.add(row)
                    }
                    filteredData=lst
                }

                var filterResults= FilterResults()
                filterResults.values=filteredData
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredData=results!!.values as MutableList<Events>
                notifyDataSetChanged()
            }
        }

    }
}
