package com.example.room

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : AppCompatActivity(),ContactAdapter.ContactClickListener {

    private lateinit var viewModel: ContactViewModel

    private lateinit var toolbarTB: Toolbar
    private lateinit var familyET: EditText
    private lateinit var phoneET: EditText
    private lateinit var recyclerViewRV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()
    }

    private fun init() {
        toolbarTB = findViewById(R.id.toolbarTB)
        setSupportActionBar(toolbarTB)

        familyET = findViewById(R.id.familyET)
        phoneET = findViewById(R.id.phoneET)

        recyclerViewRV = findViewById(R.id.recyclerViewRV)
        recyclerViewRV.layoutManager = LinearLayoutManager(this)
        val adapter = ContactAdapter(this,this)
        recyclerViewRV.adapter=adapter

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
            .getInstance(application))[ContactViewModel::class.java]
        viewModel.contacts.observe(this, Observer { list ->
            list?.let {
                adapter.updateList(it)
            }
        })

        adapter.setOnItemClickListener(object:
            ContactAdapter.OnItemClickListener {
            override fun onItemClick(contact: Contact, position: Int) {
                viewModel.deleteContact(contact)
            }
        })

    }

    override fun onItemDeleteClicked(contact: Contact) {
        viewModel.deleteContact(contact)
    }

    fun saveData(view: View){
        val family = familyET.text
        val phone = phoneET.text
        val date = Date().toString()
        if (family.isNotEmpty() && phone.isNotEmpty()) {
            viewModel.insertContact(Contact(family.toString(),phone.toString(),date))
        }
        family.clear()
        phone.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}