package com.example.testnewide.chat.di

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.testnewide.livedata.data.ThreadRepo
import com.example.testnewide.livedata.data.ThreadWithMsgContact
import javax.inject.Inject

class TestVM @Inject constructor (private val repo: TestRepo) : ViewModel(){
    fun getPackageName()= repo.getPackageName()
}
class TestRepo @Inject constructor (private val dao: TestDao){
    fun getPackageName()= dao.getPackageName()
}
class TestDao @Inject constructor (private val application: Application,private val tag:String){

    fun getPackageName():String{
        return application.packageName +tag
    }

}

//class TestDao @Inject constructor (){
//
//    fun getPackageName():String{
//        return "hah"
//    }
//
//}