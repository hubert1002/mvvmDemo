package com.example.testnewide.livedata

import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.testnewide.livedata.data.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.samples.apps.sunflower.data.AppDatabase
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.utilities.PLANT_DATA_FILENAME
import kotlinx.coroutines.coroutineScope


@androidx.room.Database(entities = [Message::class, Contact::class,Thread::class], version = 1, exportSchema = false)
@androidx.room.TypeConverters(com.google.samples.apps.sunflower.data.Converters::class)
abstract class ChatData : androidx.room.RoomDatabase() {
    abstract fun msgDao(): MsgDao
    abstract fun threadDao(): ThreadDao
    abstract fun contactDao(): ContactDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: ChatData? = null

        fun getInstance(context: android.content.Context): ChatData {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ChatData {
            return androidx.room.Room.databaseBuilder(context, ChatData::class.java,
                DATABASE_NAME
            ).addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = androidx.work.OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                                .build()
                            androidx.work.WorkManager.getInstance(context).enqueue(request)
                        }
                    })
                    .build()
        }
    }
}


const val DATA_CONTACT = "contacts.json"

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    private val TAG by lazy { SeedDatabaseWorker::class.java.simpleName }
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(DATA_CONTACT).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val contactType = object : TypeToken<List<Contact>>() {}.type
                    val contacts: List<Contact> = Gson().fromJson(jsonReader, contactType)

                    val database = ChatData.getInstance(applicationContext)
                    database.contactDao().insertAll(contacts)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }
}


