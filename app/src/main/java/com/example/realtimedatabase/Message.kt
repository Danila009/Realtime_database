package com.example.realtimedatabase

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Message(
    val message: String? = null,
    val date:String? = null
)