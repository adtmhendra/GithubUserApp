package com.hendra.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hendra.githubuser.R
import com.hendra.githubuser.model.ItemsItem
import kotlinx.android.synthetic.main.users_row.view.*

class SearchViewAdapter(private val itemsItem: ArrayList<ItemsItem>) : RecyclerView.Adapter<SearchViewAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder = SearchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.users_row, parent, false))

    override fun getItemCount() = itemsItem.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val items = itemsItem[position]

        holder.bindItem(items)
        holder.itemView.setOnClickListener{
            Toast.makeText(it.context, "${R.string.you_choose}" + items.login, Toast.LENGTH_SHORT).show()
        }
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(itemsItem: ItemsItem) {
            with(itemView) {
                tvUsername.text = itemsItem.login
                tvGithubLink.text = itemsItem.url
            }
        }
    }
}