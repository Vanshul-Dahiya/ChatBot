package com.example.chatbot.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.R
import com.example.chatbot.data.Message
import com.example.chatbot.utils.BotResponse
import com.example.chatbot.utils.Constants.OPEN_GOOGLE
import com.example.chatbot.utils.Constants.OPEN_SEARCH
import com.example.chatbot.utils.Constants.RECEIVE_ID
import com.example.chatbot.utils.Constants.SEND_ID
import com.example.chatbot.utils.Time
import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter : MessagingAdapter
    private val botlist = listOf("Alexa","Siri","Manisha","Rajjo" , "Chota Don")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView()

        clickEvents()

        val random = (0..3).random()
        customMessage("Hello ji! Today you're speaking with ${botlist[random]}." +
                " I can do basic maths stuff , flip a coin for you , google search and tell date and time." +
                " Also you can delete msg by just tapping on it." +
                " What would you like me to do ?")

    }

    private fun clickEvents(){
        btn_send.setOnClickListener {
            sendMessage()
        }
        et_message.setOnClickListener{
            GlobalScope.launch {
                delay(100)
                withContext(Dispatchers.Main){
                    rv_messages.scrollToPosition(adapter.itemCount-1)
                }
            }
        }
    }


    private fun recyclerView(){
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun sendMessage(){
        val msg  = et_message.text.toString()
        val timestamp = Time.timeStamp()

        if (msg.isNotEmpty()){
            et_message.setText("")   // empty , so that we can continue typing and not the sentence afterwards

            adapter.insertMessage(Message(msg, SEND_ID,timestamp))
            rv_messages.scrollToPosition(adapter.itemCount-1)

            botResponse(msg)
        }
    }

    private fun botResponse(message: String){
        val  timestamp = Time.timeStamp()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val response =  BotResponse.basicResponse(message)
                adapter.insertMessage(Message(response, RECEIVE_ID,timestamp))
                rv_messages.scrollToPosition(adapter.itemCount-1)

                when(response){
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm : String? = message.substringAfter("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }


    private fun customMessage(message : String) {
        GlobalScope.launch {
            delay(1500)
            withContext(Dispatchers.Main){
                val timeStamp = Time.timeStamp()
                adapter.insertMessage(Message(message,RECEIVE_ID,timeStamp))

//                update UI , when scrolling to bottom
                rv_messages.scrollToPosition(adapter.itemCount-1)

            }
        }
    }

}