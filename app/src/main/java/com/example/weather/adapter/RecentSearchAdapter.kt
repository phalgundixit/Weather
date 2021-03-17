package com.example.weather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.model.City

class RecentSearchAdapter constructor(
    private var weatherList:ArrayList<City>)
    : RecyclerView.Adapter<RecentSearchAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_recent_search_recycler_row,parent,false))
    }

    override fun getItemCount(): Int = weatherList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val weatherData=weatherList[position]
        holder.recentSearch.text=weatherData.name

    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val recentSearch: TextView =itemView.findViewById(R.id.tv_recent_search)

    }


}
