package com.example.chatbot.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.R
import com.example.chatbot.data.Message
import com.example.chatbot.utils.Constants.RECEIVE_ID
import com.example.chatbot.utils.Constants.SEND_ID
import kotlinx.android.synthetic.main.message_item.view.*

class MessagingAdapter : RecyclerView.Adapter<MessagingAdapter.MessageViewholder>() {

    var messagesList = mutableListOf<Message>()
//
    inner class MessageViewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
      init {
          itemView.setOnClickListener{
//              remove message from message list
              messagesList.removeAt(adapterPosition)
//              animation
              notifyItemRemoved(adapterPosition)
          }
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewholder {
        return MessageViewholder(LayoutInflater.from(parent.context).inflate(R.layout.message_item,parent,false))
    }

    override fun onBindViewHolder(holder: MessageViewholder, position: Int) {
//        decide which side to display message on , left or right , bot or human
        val currentMessage = messagesList[position]

        when(currentMessage.id){
            SEND_ID -> {
                holder.itemView.tv_message.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
//                ONLY our message box will be visible in current line
                holder.itemView.tv_bot_message.visibility = View.GONE
            }
            RECEIVE_ID -> {
                holder.itemView.tv_bot_message.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_message.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

//    insert message into adapter
    fun insertMessage(message : Message){
               this.messagesList.add(message)
               notifyItemInserted(messagesList.size)
//    notifyDataSetChanged()  -> checks all the items in RecyclerView if anything is changed
//    if changed , it updates the RCV .........Also it doesn't provide animation unlike NtfyItmInrtd
    }

}

