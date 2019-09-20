package com.example.testnewide.chat.di

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.testnewide.TestApp
import com.example.testnewide.chat.viewmodel.MsgListViewModelFactory
import com.example.testnewide.chat.viewmodel.MsgVM
import com.example.testnewide.chat.viewmodel.ThreadVM
import com.example.testnewide.livedata.ChatData
import com.example.testnewide.livedata.data.MsgRepo
import javax.inject.Inject

 open class BaseActivity:AppCompatActivity(){

    val factory:ViewModelProvider.Factory by lazy {
        if (application is TestApp) {
            val mainApplication = application as TestApp
            return@lazy mainApplication.factory
        }else{
            throw IllegalStateException("application is not PaoApp")
        }
    }

    fun <T : ViewModel> getInjectViewModel (c:Class<T>)= ViewModelProviders.of(this,factory).get(c)

}


class TestDaggerActivity :BaseActivity(){
    private val viewModel1: TestVM by viewModels {
        factory
    }

    private val mViewModel2: TestVM by lazy {
        getInjectViewModel(TestVM::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("liuwei",""+viewModel1.getPackageName()+mViewModel2.getPackageName())
    }
}