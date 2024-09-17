package com.project.basiccalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TitleAdapter(
    private val titles: List<String>,
    private val itemClickListener: (String) -> Unit
) : RecyclerView.Adapter<TitleAdapter.TitleViewHolder>() {

    class TitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return TitleViewHolder(view)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        holder.titleTextView.text = titles[position]
        holder.itemView.setOnClickListener {
            itemClickListener(titles[position])
        }
    }

    override fun getItemCount(): Int = titles.size
}