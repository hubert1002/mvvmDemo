package com.example.testnewide.livedata.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.testnewide.livedata.ChatData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactRepo @Inject constructor(private val application: Application){
    fun getContactDao():ContactDao{
        return ChatData.getInstance(application).contactDao()
    }
    fun getContact() = getContactDao().getContact()
    fun getContact(id:String) = getContactDao().getContact(id)
    fun getContactWithAge(age:Int) = getContactDao().getContactWithAge(age)
    suspend fun insertItem(item: Contact) {
        withContext(Dispatchers.IO) {
            getContactDao().insertContact(item)
        }
    }
    suspend fun remove(item: Contact) {
        withContext(Dispatchers.IO) {
            getContactDao().deleteContact(item)
        }
    }


    companion object {
        // For Singleton instantiation
        @Volatile private var instance: ContactRepo? = null

        fun getInstance(application: Application) =
            instance ?: synchronized(this) {
                instance ?: ContactRepo(application).also { instance = it }
            }
    }

}

class ThreadRepo private constructor(private val dao: ThreadDao){
    fun getThreads() = dao.getThreads()
    fun getThreads(id:String) = dao.getThread(id)
    fun getThreadsWithContact(id:String) = dao.getThreadsWithContact(id)
    fun getContactWithThread() = dao.getContactWithThread()
    fun getThreadInfo() = dao.getThreadInfo()

    suspend fun insertItem(item: Thread) {
        withContext(Dispatchers.IO) {
            dao.insertThread(item)
        }
    }
    suspend fun remove(item: Thread) {
        withContext(Dispatchers.IO) {
            dao.deleteThread(item)
        }
    }

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: ThreadRepo? = null
        fun getInstance(dao: ThreadDao) =
            instance ?: synchronized(this) {
                instance ?: ThreadRepo(dao).also { instance = it }
            }
    }
}


class MsgRepo private constructor(private val dao: MsgDao){
    fun getContactWithMsg() = dao.getContactWithMsg()
    fun getContactWithMsg(contactId:String) = dao.getContactWithMsg(contactId)
//    fun getContactWithMsg(contactId:String):LiveData<List<ContactWithMsg>>{
//
//
//
////        var dateori :List<ContactWithMsg> = dao.getContactWithMsg2(contactId)
////        Log.e("test","dateori"+dateori.size)
//        var data: LiveData<List<ContactWithMsg>>  = dao.getContactWithMsg(contactId)
//
//        return data
//    }

    fun getMsg(id:String) = dao.getMsgWithContact(id)
    suspend fun insertItem(item: Message) {
        withContext(Dispatchers.IO) {
            dao.insertMsg(item)
        }
    }
    suspend fun insertItemAndUpdateThread(item: Message) {
        withContext(Dispatchers.IO) {
            dao.insertAndUpdateThread(item)
        }
    }

    suspend fun remove(item: Message) {
        withContext(Dispatchers.IO) {
            dao.deleteMsg(item)
        }
    }

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: MsgRepo? = null

        fun getInstance(dao: MsgDao) =
            instance ?: synchronized(this) {
                instance ?: MsgRepo(dao).also { instance = it }
            }
    }
}