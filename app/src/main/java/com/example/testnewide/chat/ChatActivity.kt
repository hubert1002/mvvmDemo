package com.example.testnewide.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.testnewide.BaseActivity
import com.example.testnewide.R
import com.example.testnewide.chat.viewmodel.ContactListViewModel
import com.example.testnewide.chat.viewmodel.ContactListViewModelFactory
import com.example.testnewide.livedata.ChatData
import com.example.testnewide.livedata.data.ContactRepo
import com.google.samples.apps.sunflower.data.GardenPlanting
import kotlinx.android.synthetic.main.activity_garden_test1.*

class ChatActivity:BaseActivity(){

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        navController = findNavController(R.id.chat_nav_fragment)
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