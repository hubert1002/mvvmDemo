package com.example.testnewide.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testnewide.R
import com.example.testnewide.chat.viewmodel.ThreadInfoVM
import com.example.testnewide.chat.viewmodel.ThreadVM
import com.example.testnewide.databinding.ListItemContactBinding
import com.example.testnewide.databinding.ListItemMsgBinding
import com.example.testnewide.databinding.ListItemThreadBinding
import com.example.testnewide.livedata.data.Contact
import com.example.testnewide.livedata.data.ContactWithThread
import com.example.testnewide.livedata.data.Message
import com.google.samples.apps.sunflower.PlantListFragmentDirections

class ContactAdapter:ListAdapter<Contact,ContactAdapter.ViewHolder>(ContactDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemContactBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = getItem(position)
        holder.apply {
            bind(createOnClickListener(plant.contactId), plant)
            itemView.tag = plant
        }
    }

    private fun createOnClickListener(contactId: String): View.OnClickListener {
        return View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("id",contactId)
            it.findNavController().navigate(R.id.action_jump_contact_detail,bundle)
        }
    }

    class ViewHolder(
        private val binding: ListItemContactBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item: Contact) {
            binding.apply {
                clickListener = listener
                contact = item
                executePendingBindings()
            }
        }
    }
}

private class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {

    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.contactId == newItem.contactId
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}


class ThreadAdapter:ListAdapter<ContactWithThread,ThreadAdapter.ViewHolder>(ThreadDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ThreadAdapter.ViewHolder(
            ListItemThreadBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(createOnClickListener(item.contact.contactId), item)
            itemView.tag = item
        }
    }


    class ViewHolder(private val binding : ListItemThreadBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener,item: ContactWithThread){
            binding.apply {
                clickListener = listener
                viewModel = item
                executePendingBindings()
            }
        }
    }

    private fun createOnClickListener(contactId: String): View.OnClickListener {
        return View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("id",contactId)
            it.findNavController().navigate(R.id.action_from_thread_to_contact_detail,bundle)
        }
    }
}

private class ThreadDiffCallback : DiffUtil.ItemCallback<ContactWithThread>() {

    override fun areItemsTheSame(oldItem: ContactWithThread, newItem: ContactWithThread): Boolean {
        return oldItem.contact.contactId == newItem.contact.contactId
    }

    override fun areContentsTheSame(oldItem: ContactWithThread, newItem: ContactWithThread): Boolean {
        return oldItem.contact == newItem.contact
    }
}


class MsgAdapter:ListAdapter<Message,MsgAdapter.ViewHolder>(MsgDiffCallback()){

    var  msgContact = Contact("id",0,"default","des","error")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemMsgBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(createOnClickListener(msgContact.contactId), msgContact,item)
            itemView.tag = item
        }
    }


    class ViewHolder(private val binding : ListItemMsgBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener,item: Contact,msg:Message){
            binding.apply {
                clickListener = listener
                contact = item
                message = msg
                executePendingBindings()
            }
        }
    }

    private fun createOnClickListener(contactId: String): View.OnClickListener {
        return View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("id",contactId)
            it.findNavController().navigate(R.id.action_from_msg_to_contact_detail,bundle)
        }
    }
}

private class MsgDiffCallback : DiffUtil.ItemCallback<Message>() {

    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }
}