package com.example.testnewide.chat.di

import android.app.Application
import androidx.lifecycle.*
import com.example.testnewide.livedata.ChatData
import com.example.testnewide.livedata.data.Contact
import com.example.testnewide.livedata.data.ContactDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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


