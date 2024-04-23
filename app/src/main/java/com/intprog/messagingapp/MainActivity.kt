package com.intprog.messagingapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var sharedPreferences: SharedPreferences
    private var messagesList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //declare sa shared preference
        sharedPreferences = getSharedPreferences("messages", Context.MODE_PRIVATE)

        //declare sa mga variables
        val listView = findViewById<ListView>(R.id.listView)
        val text = findViewById<EditText>(R.id.messageEditText)
        val sendButton = findViewById<Button>(R.id.sendButton)
        val user2Button = findViewById<Button>(R.id.user2Button)
        val clearButton = findViewById<Button>(R.id.clearButton)

        //load  kada create sa app
        loadMessages()

        // declare ug adapter
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, messagesList)
        listView.adapter = adapter

        sendButton.setOnClickListener {
            val message = "User1: " + text.text.toString()
            messagesList.add(message)
            text.setText("")
            saveMessages()
            adapter.notifyDataSetChanged()
        }

        user2Button.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }

        clearButton.setOnClickListener {
            messagesList.clear()
            saveMessages()
            adapter.notifyDataSetChanged()
        }

    }

    private fun saveMessages() {
        val editor = sharedPreferences.edit()
        val messages = messagesList.joinToString("\n")
        editor.putString("messages", messages)
        editor.apply()
    }

    private fun loadMessages() {
        val messages = sharedPreferences.getString("messages", "")
        messagesList.clear()
        messagesList.addAll(messages?.split("\n") ?: emptyList())
    }
}
