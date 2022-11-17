package com.example.networktest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class NetworkAdapter(var context: Context,var contactList: List<ListResponse>):
    RecyclerView.Adapter<NetworkAdapter.ViewHolder>() {
    val TAG = "NetworkAdapter"

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val id : TextView = view.findViewById(R.id.id)
        val name: TextView = view.findViewById(R.id.name)
        val followers: TextView = view.findViewById(R.id.followers)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("NetworkAdapter","onCreateViewHolder")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.network_item,parent,false)
        //return ViewHolder(view)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val contact = contactList[position]
            Log.d(TAG,"onClick"+position)
            val context = view.context
            val intent = Intent(context,NetworkItemActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("Int",1)
            bundle.putParcelable("contact",contact)
            Log.d(TAG,"setOnClickListener " + contact)
            intent.putExtra("bundle",bundle)
            context.startActivity(intent)
        }
        return viewHolder
        /*val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val list = contactList[position]
            val intent = Intent(parent.context,NetworkActivity::class.java).apply {
                putExtra("id",list.id)
                putExtra("name",list.name)
                putExtra("followers",list.followers)
            }
            fragment.startActivity(intent)
            fragment.activity?.finish()
        }
        return holder*/

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("NetworkAdapter","onBindViewHolder")
        val list = contactList[position]
        holder.id.text = list.id.toString()
        holder.name.text = list.name
        holder.followers.text = list.followers.toString()
        Log.d("NetworkAdapter","contactList")
    }

    override fun getItemCount() = contactList.size

}