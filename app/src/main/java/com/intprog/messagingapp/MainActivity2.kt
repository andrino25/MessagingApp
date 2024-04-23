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

class MainActivity2 : AppCompatActivity() {

    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var sharedPreferences: SharedPreferences
    private var messagesList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        sharedPreferences = getSharedPreferences("messages", Context.MODE_PRIVATE)
        val listView = findViewById<ListView>(R.id.listView)
        val text = findViewById<EditText>(R.id.messageEditText)
        val sendButton = findViewById<Button>(R.id.sendButton)
        val user1Button = findViewById<Button>(R.id.user1Button)
        val clearButton = findViewById<Button>(R.id.clearButton)

        loadMessages()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, messagesList)
        listView.adapter = adapter

        sendButton.setOnClickListener {
            val message = "User2: " + text.text.toString()
            messagesList.add(message)
            text.setText("")
            saveMessages()
            adapter.notifyDataSetChanged()
        }

        user1Button.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
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
