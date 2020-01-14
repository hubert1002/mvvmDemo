package com.example.testnewide

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.DragEvent
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import com.example.lwframework.GlideHelper

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
             Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            jumpActivity()






        }
        var url = "https://pics1.baidu.com/feed/d1160924ab18972ba4f1a80a94381a8c9c510a25.jpeg?token=b32a0c94ffc077aa0d49543864d7a86f&s=EFC2FD1E595E54CC42E9ADEF03005037"
        GlideHelper.bindImageFromUrl(imageView,url)
    }

    fun jumpActivity(){
//        var hah = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION)
//        ActivityCompat.requestPermissions(MainActivity@this, hah, 0)

//
        var intent = Intent()
        intent.setClass(MainActivity@this,TestActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
