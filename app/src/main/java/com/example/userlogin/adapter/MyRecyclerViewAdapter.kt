package com.example.userlogin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.example.userlogin.R
import com.example.userlogin.model.User
import kotlinx.android.synthetic.main.item_user_messenger.view.*
import java.util.*


open class MyRecyclerViewAdapter(var list: ArrayList<User>, val context:Context) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {

    private val mContext = context
    private lateinit var mListener: OnItemClickListener
    private var filteredItems = list

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(clickListener: OnItemClickListener) {
        mListener = clickListener
    }

    class ViewHolder(itemView: View, clickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_messenger, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            tv_user_profile.text = list[position].displayName
            ConvertImage(mContext).urlToImage(list[position].imgUrl, image_profile_messenger)
        }
        holder.itemView.setOnClickListener {
            mListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults? {
                val oReturn = FilterResults()
                val results: ArrayList<User> = ArrayList()
                if (constraint != null && constraint.isNotEmpty()) {
                    if (filteredItems.size > 0) {
                        for (cd in filteredItems) {
                            if (cd.displayName.lowercase(Locale.ROOT)
                                    .contains(constraint.toString().lowercase(Locale.getDefault()))
                            ) results.add(cd)
                        }
                    }
                    oReturn.values = results
                    oReturn.count = results.size
                } else {
                    oReturn.values = filteredItems
                    oReturn.count = filteredItems.size
                }
                return oReturn
            }

            override fun publishResults(
                constraint: CharSequence?,
                results: FilterResults
            ) {
                list = ArrayList(results.values as ArrayList<User>)
                notifyDataSetChanged()

            }
        }
    }





}
