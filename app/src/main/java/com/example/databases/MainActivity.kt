package com.example.databases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextUrl: EditText
    private lateinit var buttonApply: Button
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var picasso: Picasso

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("USER_INFO")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        editTextUrl = findViewById(R.id.editTextUrl)
        buttonApply = findViewById(R.id.buttonApply)
        imageView = findViewById(R.id.imageView)
        textView = findViewById(R.id.textView)

        buttonApply.setOnClickListener{

            val name = editTextName.text.toString()
            val url = editTextUrl.text.toString()

            val personInfo = PersonInfo(name, url)

            db.child(auth.currentUser?.uid!!).setValue(personInfo)

        }
        db.child(auth.currentUser?.uid!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userInfo = snapshot.getValue(PersonInfo::class.java) ?: return
                textView.text = userInfo.name
                Picasso.get()
                    .load(editTextUrl.text.toString())
                    .into(imageView)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}