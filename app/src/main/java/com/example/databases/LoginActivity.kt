package com.example.databases

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    private var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener{

            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(this,"Success!", Toast.LENGTH_SHORT).show()
                        Intent(this, MainActivity::class.java).also { startActivity(it) }
                    }else{
                        Toast.makeText(this,"Error!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}