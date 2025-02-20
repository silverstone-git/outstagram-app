package com.cyto.outstagram.ui.home.model
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeAdapter(private var homeItems: List<HomeItem>) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(android.R.id.text1)
        val descriptionTextView: TextView = itemView.findViewById(android.R.id.text2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return HomeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentItem = homeItems[position]
        holder.titleTextView.text = currentItem.title
        holder.descriptionTextView.text = currentItem.description
    }

    override fun getItemCount(): Int = homeItems.size

    fun updateData(newItems: List<HomeItem>) {

        // While liking the post
        homeItems = newItems
        notifyDataSetChanged()
    }
}