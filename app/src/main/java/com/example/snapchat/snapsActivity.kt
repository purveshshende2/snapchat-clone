package com.example.snapchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class snapsActivity : AppCompatActivity() {

    val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snaps)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.snaps,menu)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item?.itemId == R.id.createSnap){
            val intent = Intent(this,snapDashActivity::class.java)
            startActivity(intent)

        } else if (item?.itemId == R.id.logout){
            auth.signOut() // sign out with firebase.
            finish()
        }


        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        auth.signOut() // if any one press back button is sign out
    }
}