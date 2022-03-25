package com.example.realtimedatabase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {

    private lateinit var databaseRepository: DatabaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databaseRepository = ViewModelProvider(this).get(DatabaseViewModel::class.java)

        setContent {
            val user = remember { mutableStateOf(listOf<User>()) }

            user.value = databaseRepository.allUser()
//            lifecycleScope.launchWhenStarted {
//                databaseRepository.responseUser.onEach {
//                    Log.e("Realtime:", it.toString())
//                    user.value = it
//                }.collect()
//            }

            Column {
                LazyColumn(content = {

                    item {
                        OutlinedButton(onClick = {
                            var username = "0"
                            for (i in 1 until 10){
                                username += (0..i).random()
                            }
                            databaseRepository.addUser(
                                users = User(
                                    username = username
                                )
                            )
                        }) {
                            Text(text = "Add")
                        }
                    }

                    items(user.value){ item ->
                        Text(text = item.username.toString())
                        Row {
                            OutlinedButton(onClick = {
                                var username = "0"
                                for (a in 1 until 10){
                                    username += (0..a).random()
                                }

                                databaseRepository.updateUser(
                                    User(
                                        id = item.id,
                                        username = "updateUser $username",
                                        age = 1,
                                        messages = listOf()
                                    ), item.id.toString()
                                ) }) {
                                Text(text = "Update user")
                            }

                            OutlinedButton(onClick = {
                                databaseRepository.deleteUser(item.id.toString())

                            }) {
                                Text(text = "Delete user")
                            }
                        }
                    }
                })
            }
        }
    }
}
