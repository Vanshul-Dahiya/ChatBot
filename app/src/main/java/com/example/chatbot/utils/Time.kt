package com.example.chatbot.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


object Time {
    //  it will retrieve time for user on asking time

    fun timeStamp() : String{
        val timeStamp = Timestamp(System.currentTimeMillis())

//        format time
        val simpleDateFormat = SimpleDateFormat("HH:mm")

        val time = simpleDateFormat.format(Date(timeStamp.time))

        return time.toString()
    }

}