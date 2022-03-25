package com.example.realtimedatabase

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var id:String? = null,
    val username:String? = null,
    val age:Int? = null,
    val messages:List<Message>? = null,
){
    @Exclude
    fun toMap():Map<String, Any?>{
        return mapOf(
            "username" to username,
            "age" to age,
            "messages" to messages
        )
    }
}