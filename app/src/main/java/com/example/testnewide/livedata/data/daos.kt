package com.example.testnewide.livedata.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.samples.apps.sunflower.data.AppDatabase
import com.google.samples.apps.sunflower.data.GardenPlanting
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.utilities.PLANT_DATA_FILENAME
import kotlinx.coroutines.coroutineScope

//
//fun test(context: Context){
//    ChatLiveData.getInstance(context.applicationContext).contactDao()
//}
const val DATABASE_NAME = "msg-db"
@Dao
interface MsgDao {
    @Query("SELECT * FROM tb_messages")
    fun getMessages(): androidx.lifecycle.LiveData<List<Message>>

    @Query("SELECT * FROM tb_messages WHERE mid = :mid")
    fun getMsg(mid: String): androidx.lifecycle.LiveData<Message>

    @Query("SELECT * FROM tb_messages WHERE contactId = :contactId")
    fun getMsgWithContact(contactId: String): androidx.lifecycle.LiveData<Message?>


    @Transaction
    @Query("SELECT * FROM tb_contact")
    fun getContactWithMsg(): androidx.lifecycle.LiveData<List<ContactWithMsg>>

    @Transaction
    @Query("SELECT * FROM tb_contact WHERE contactId = :contactId")
    fun getContactWithMsg(contactId:String): LiveData<List<ContactWithMsg>>

    @Transaction
    @Query("SELECT * FROM tb_contact WHERE contactId = :contactId")
    fun getContactWithMsg2(contactId:String): List<ContactWithMsg>

    @Insert
    fun insertMsg(msg: Message): Long

    @Delete
    fun deleteMsg(msg: Message)
}

@Dao
interface ContactDao{
    @Query("SELECT * FROM tb_contact")
    fun getContact(): androidx.lifecycle.LiveData<List<Contact>>

    @Query("SELECT * FROM tb_contact WHERE contactId = :contactId")
    fun getContact(contactId: String): androidx.lifecycle.LiveData<Contact>

    @Query("SELECT * FROM tb_contact WHERE age = :age ORDER BY name")
    fun getContactWithAge(age: Int): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(contacts: List<Contact>)

    @Delete
    fun deleteContact(contact: Contact)
}


@Dao
interface ThreadDao {
    @Query("SELECT * FROM tb_thread")
    fun getThreads(): androidx.lifecycle.LiveData<List<Thread>>

    @Query("SELECT * FROM tb_thread WHERE id = :id")
    fun getThread(id: String): androidx.lifecycle.LiveData<Thread>

    @Query("SELECT * FROM tb_thread WHERE contactId = :contactId")
    fun getThreadsWithContact(contactId: String): androidx.lifecycle.LiveData<Thread?>

    /**
     * This query will tell Room to query both the [Plant] and [GardenPlanting] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM tb_contact")
    fun getContactWithThread(): androidx.lifecycle.LiveData<List<ContactWithThread>>

    @Insert
    fun insertThread(thread: Thread): Long

    @Delete
    fun deleteThread(thread: Thread)

    @Transaction
    @Query("SELECT * FROM tb_thread")
    fun getThreadInfo(): androidx.lifecycle.LiveData<List<ThreadWithMsgContact>>

}



