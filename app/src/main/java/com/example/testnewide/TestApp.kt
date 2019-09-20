package com.example.testnewide

import android.app.Application
import com.example.testnewide.chat.di.APPViewModelFactory
import com.example.testnewide.chat.di.AppModule
import com.example.testnewide.chat.di.DaggerAppComponent
import javax.inject.Inject

class TestApp :Application(){
    @Inject
    lateinit var factory: APPViewModelFactory

    override fun onCreate() {
        super.onCreate()
        //...
        DaggerAppComponent.builder().application(this).tag("hahha").build().inject(this)
//        DaggerAppComponent.builder().appModule(appModule).build().inject(this)
    }
}

