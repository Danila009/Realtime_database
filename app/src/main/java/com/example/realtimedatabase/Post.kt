package com.example.realtimedatabase

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Post(
    val title:String? = null,
    val description: String? = null,
    val user:User? = null,
    val date:String? = null
)
