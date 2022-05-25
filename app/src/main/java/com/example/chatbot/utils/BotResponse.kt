package com.example.chatbot.utils

import com.example.chatbot.data.Message
import com.example.chatbot.utils.Constants.OPEN_GOOGLE
import com.example.chatbot.utils.Constants.OPEN_SEARCH
import java.lang.Exception

object BotResponse {

    fun basicResponse(_message: String) : String{

        val random = (0..3).random()
        val message = _message.toLowerCase()

        return when{

//          hello
            message.contains("hello") -> {
                when(random){
                    0 -> "JAI BHOLE KI "
                    1 -> "JAI SHREE RAM "
                    2 -> "JAI MATA DI "
                    3 -> "Hey there!"
                    else -> "Hello (Error) "
                }
            }

//            how are you
            message.contains("how are you") -> {
                when(random){
                    0 -> "Been good , thanks for asking!"
                    1 -> "Pretty Awesome"
                    2 -> "Just fine , how about you?"
                    3 -> "Maja ma"
                    else -> "Sed (Error) "
                }
            }

            message.contains("flip") && message.contains("coin") -> {
                val r = (0..1).random()
                val result = if (r == 0) "heads" else "tails"
                "Flipped a coin for you , it's $result"
            }

//            Solve maths
            message.contains("solve")  -> {
               val equation : String? = message.substringAfter("solve")
                return try {
                    // if it doesn't work , return zero/Text
                    val answer = SolveMath.solveMath(equation ?: "I'm kinda weak in maths. Try asking simpler questions like with 2 variables.")
                    answer.toString()
                }catch (e : Exception){
                    "Sorry , Can't solve this. I'm kinda weak in maths."
                }
            }

//            Tells current time
            message.contains("time") && message.contains("?") -> {
                Time.timeStamp()
            }

//            opens google
            message.contains("open") && message.contains("google") -> {
                OPEN_GOOGLE
            }
//            searchs item
            message.contains("search")  -> {
                OPEN_SEARCH
            }


            else ->{
                when(random){
                    0 -> "I'm afraid , I won't be able to answer you."
                    1 -> "I've zero idea what are you talking about. Change the topic!"
                    2 -> "I don't know about it. Try asking something different."
                    3 -> ""
                    else -> "Out of reach , sorry!"
                }
            }
        }


    }

}