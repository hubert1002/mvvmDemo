package com.example.testnewide.livedata.data

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactRepo private constructor(private val dao: ContactDao){
    fun getContact() = dao.getContact()
    fun getContact(id:String) = dao.getContact(id)
    fun getContactWithAge(age:Int) = dao.getContactWithAge(age)
    suspend fun insertItem(item: Contact) {
        withContext(Dispatchers.IO) {
            dao.insertContact(item)
        }
    }
    suspend fun remove(item: Contact) {
        withContext(Dispatchers.IO) {
            dao.deleteContact(item)
        }
    }

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: ContactRepo? = null

        fun getInstance(dao: ContactDao) =
            instance ?: synchronized(this) {
                instance ?: ContactRepo(dao).also { instance = it }
            }
    }
}

class ThreadRepo private constructor(private val dao: ThreadDao){
    fun getThreads() = dao.getThreads()
    fun getThreads(id:String) = dao.getThread(id)
    fun getThreadsWithContact(id:String) = dao.getThreadsWithContact(id)
    fun getContactWithThread() = dao.getContactWithThread()

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