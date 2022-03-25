package com.example.realtimedatabase

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class DatabaseViewModel:ViewModel() {

    private val database = Firebase.database
    private val userRef = database.getReference("user")

//    private val _responseUser:MutableStateFlow<ArrayList<User>> = MutableStateFlow(ArrayList())
//    val responseUser:StateFlow<List<User>> = _responseUser.asStateFlow()

    fun addUser(users: User){
        val key = userRef.push().key
        users.id = key
        val children = hashMapOf<String, Any>(
            "/$key" to users
        )
        userRef.updateChildren(children)
    }

    fun allUser():List<User>{
        val users = mutableListOf<User>()
        viewModelScope.launch {
            userRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.e("Realtime:", snapshot.value.toString())
                    Log.e("Realtime:", snapshot.children.toString())
                    for (i in snapshot.children){
                        Log.e("Realtime:", i.getValue(User::class.java).toString())
                        users.add(i.getValue<User>()!!)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
        return users
    }

    fun updateUser(user: User, id:String){
        userRef.child(id).updateChildren(user.toMap())
            .addOnSuccessListener{
                Log.e("Realtime:", "ok")
            }
            .addOnFailureListener{
                Log.e("Realtime:", it.message.toString())
            }
    }

    fun deleteUser(id:String){
        userRef.child(id).removeValue()
            .addOnSuccessListener{
                Log.e("Realtime:", "ok")
            }
            .addOnFailureListener{
                Log.e("Realtime:", it.message.toString())
            }
    }
}