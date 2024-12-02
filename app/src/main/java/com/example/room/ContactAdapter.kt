package com.example.room

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(private val context: Context,private val listener:ContactClickListener):RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(contact: Contact, position: Int)
    }

    val contacts = ArrayList<Contact>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList:List<Contact>) {
        contacts.clear()
        contacts.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        private val familyTV:TextView = itemView.findViewById(R.id.familyTV)
        private val phoneTV:TextView = itemView.findViewById(R.id.phoneTV)
        private val dataTV:TextView = itemView.findViewById(R.id.dataTV)
        val deleteIV:ImageView = itemView.findViewById(R.id.deleteIV)

        fun bind(contact:Contact){
            familyTV.text = contact.family
            phoneTV.text = contact.phone
            dataTV.text = contact.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val viewHolder = ContactViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item,parent,false))
        viewHolder.deleteIV.setOnClickListener{
            listener.onItemDeleteClicked(contacts[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentContact = contacts[position]
        holder.bind(currentContact)
        holder.itemView.setOnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemClick(currentContact, position)
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface ContactClickListener {
        fun onItemDeleteClicked(contact: Contact)
    }
}