package com.example.snapchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    var emailEditText: EditText? = null
    var passwordEditText: EditText? = null
    val auth = Firebase.auth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        getSupportActionBar()?.hide() // hide the title bar
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN) //enable full screen
        setContentView(R.layout.activity_main)


        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        if(auth.currentUser != null) {
            logIn()
        }
    }

    fun goClicked(view: View){
        //check if we can log in the user
        auth.signInWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                  logIn()
                } else {
                    //sign up please!
                    auth.createUserWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString()).addOnCompleteListener(this) { task ->
                        if(task.isSuccessful){
                            // Add to database
                                //-----------firebase database ----------------------
                                    // Edit your rules and you can start your database.
                                        // toString() in the below line it's doesn't matters you can play with your database.
                                        FirebaseDatabase.getInstance().getReference().child("users").child(task.result?.user?.uid.toString()).child("email").setValue(emailEditText?.text.toString())
                                    logIn()
                        } else {
                            Toast.makeText(this,"Login Failed. Try Again",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

    }

    fun logIn(){
       val intent = Intent(this,snapsActivity::class.java)
        startActivity(intent)
    }
}