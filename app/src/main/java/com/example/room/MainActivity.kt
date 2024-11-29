package com.example.room

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var db: NoteDatabase? = null

    private lateinit var toolbarTB: Toolbar
    private lateinit var familyET: EditText
    private lateinit var phoneET: EditText
    private lateinit var saveBTN: Button
    private lateinit var textView: TextView

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
        saveBTN = findViewById(R.id.saveBTN)
        textView = findViewById(R.id.textView)
        db = NoteDatabase.getDatabase(this)
        readDatabase(db!!)

    }

    override fun onResume() {
        super.onResume()
        saveBTN.setOnClickListener {
            val family = familyET.text
            val phone = phoneET.text
            if (family.isNotEmpty() && phone.isNotEmpty()) {
                val note = Note(family.toString(), phone.toString())
                addNote(db!!, note)
                readDatabase(db!!)
                family.clear()
                phone.clear()
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun addNote(db: NoteDatabase, note: Note) = GlobalScope.async {
        db.getNoteDao().insert(note)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun readDatabase(db: NoteDatabase) = GlobalScope.async {
        delay(10L)
        textView.text = ""
        val list = db.getNoteDao().getAllNotes()
        list.forEach { i -> textView.append("${i.family} : ${i.phone}\n") }
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