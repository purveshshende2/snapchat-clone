package com.example.snapchat

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.loader.content.AsyncTaskLoader
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.net.HttpURLConnection
import java.net.URL

class ViewSnapActivity : AppCompatActivity() {

    var messageTextView: TextView? = null
    var snapImageView : ImageView? = null

    val auth = Firebase.auth




    //------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) { //|
        super.onCreate(savedInstanceState)               //|
        setContentView(R.layout.activity_view_snap)      //|
    //------------------------------------------------------


        messageTextView = findViewById(R.id.messageTextView)
        snapImageView = findViewById(R.id.snapImageView)

        messageTextView?.text = intent.getStringExtra("message")


        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

        val task = ImageDownloader()
        val myImage: Bitmap
        try {
            myImage = task.execute(intent.getStringExtra("imageUrl")).get()

            snapImageView?.setImageBitmap(myImage)
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    //This Downloader for Snapn that we've sent.
    inner class ImageDownloader : AsyncTask<String,Void,Bitmap>() {
        override fun doInBackground(vararg p0: String?): Bitmap? {
            try {
                val url = URL(p0[0])

                val connection = url.openConnection() as HttpURLConnection

                connection.connect()

                val `in` = connection.inputStream

                return BitmapFactory.decodeStream(`in`)

            } catch (e:Exception){
                e.printStackTrace()
                return null
            }
        }
    }
    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^


    //--------------------------------------------------------------------------------------
    //on Backpress all database and images are deletes like real snapchat
    override fun onBackPressed() {
        super.onBackPressed()

        FirebaseDatabase.getInstance().getReference().child("users").child(auth.currentUser?.uid!!).child("snaps").child(
            intent.getStringExtra("snapKey")!!
        ).removeValue()

        FirebaseStorage.getInstance().getReference().child("images").child(intent.getStringExtra("imageName")!!).delete()


    }
    //-------------------------------------------------------------------------------------
}