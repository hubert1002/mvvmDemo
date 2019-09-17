package com.example.testnewide

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.testnewide.chat.ChatActivity
import com.example.testnewide.livedata.ChatData
import kotlinx.android.synthetic.main.actiivity_test.*
import kotlinx.android.synthetic.main.activity_garden_test1.*

abstract class BaseActivity : AppCompatActivity(){
    val tag:String = TestActivity::class.java.name
}

class TestActivity :BaseActivity(){
    var listener = View.OnClickListener{  view->
        Toast.makeText(TestActivity@this,"click"+view.id,Toast.LENGTH_LONG).show()
        when(view.id){
            R.id.btn1->{
                var intent = Intent()
                intent.setClass(MainActivity@this,NaviActivity::class.java)
                startActivity(intent)
            }
            R.id.btn2->{
                var intent = Intent()
                intent.setClass(MainActivity@this,ChatActivity::class.java)
                startActivity(intent)

                ChatData.getInstance(MainActivity@this.applicationContext).contactDao()
            }
            else->{

            }
        }
        view.alpha = 0.5f
        Log.i(tag,"OnClickListener")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actiivity_test)
        btn1.setOnClickListener(listener)
        btn2.setOnClickListener(listener)
    }
}


class NaviActivity:BaseActivity(){
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_garden_test1)

        navController = findNavController(com.google.samples.apps.sunflower.R.id.garden_nav_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)

        // Set up ActionBar
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Set up navigation menu
        navigation_view.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}


class MyNaviActivity:BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}