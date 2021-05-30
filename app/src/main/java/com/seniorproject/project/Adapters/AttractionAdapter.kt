package com.seniorproject.project.Adapters

import android.content.Context
import android.location.Location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.seniorproject.project.Interface.onItemClickListener
import com.seniorproject.project.R
import com.seniorproject.project.models.Restaurants

class AttractionAdapter(private val currentLatLng: LatLng, private val rssObject: MutableList<Restaurants>, private val mContext: Context, private val listener: onItemClickListener): RecyclerView.Adapter<AttractionAdapter.FeedViewHolders>()
{   private var filteredData=rssObject
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolders {

        val itemView = inflater.inflate(R.layout.card_attraction,parent,false)
        return FeedViewHolders(itemView)
    }

    private val inflater: LayoutInflater

    init{
        inflater = LayoutInflater.from(mContext)
    }

    override fun onBindViewHolder(holder: FeedViewHolders, position: Int) {
        holder.txtTitle.text = filteredData[position].Name
        holder.txtTitle1.text = filteredData[position].Location
        holder.txtTitle2.text = filteredData[position].Category
        //holder.txtTitle3.text = rssObject[position].date
        holder.txtTitle4.text = filteredData[position].CalculatedDis.toString()+" km"
        holder.txtTitle5.text = filteredData[position].Rating.toString()

//        var evelat = filteredData[position].Latitude.toString()
//        var evelong = filteredData[position].Longitude.toString()
//
//        val loc1 = Location("")
//        loc1.setLatitude(currentLatLng.latitude)
//        loc1.setLongitude(currentLatLng.longitude)
//
//        val loc2 = Location("")
//        loc2.setLatitude(evelat.toDouble())
//        loc2.setLongitude(evelong.toDouble())
//
//        val distanceInMeters: Float = loc1.distanceTo(loc2)
//        var distanceInKm = String.format("%.2f", (distanceInMeters / 1000)).toFloat()
//
//        holder.txtTitle4.text = distanceInKm.toString() + "km"

        var result = when (filteredData[position].imageURL) {
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
        return filteredData.size
    }
    inner class FeedViewHolders(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener
    {

        var txtTitle: TextView = itemView.findViewById(R.id.textView)
        var txtTitle1: TextView = itemView.findViewById(R.id.textView1)
        var txtTitle2: TextView = itemView.findViewById(R.id.textView2)
        //var txtTitle3: TextView
        var txtTitle4: TextView = itemView.findViewById(R.id.attdistance)
        var txtTitle5: TextView = itemView.findViewById(R.id.ratetxtatt)
        var img:ImageView = itemView.findViewById(R.id.imageShow)
        // var imgbtn:ImageView


        //private var itemClickListener: ItemClickListener? = null

        init {

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
                    var lst= mutableListOf<Restaurants>()
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
                filteredData=results!!.values as MutableList<Restaurants>
                notifyDataSetChanged()
            }
        }

    }
}
