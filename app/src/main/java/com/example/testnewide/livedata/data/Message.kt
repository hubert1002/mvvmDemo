package com.example.testnewide.livedata.data

import androidx.room.*
import java.util.*

@Entity(
    tableName = "tb_messages",
    foreignKeys = [ForeignKey(entity = Contact::class, parentColumns = ["id"], childColumns = ["contactId"])],
    indices = [Index("mid"),Index("contactId")]
)
data class Message(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
    val mid: String,
    val contactId: String,
    val date: Calendar = Calendar.getInstance(),
    val msg:String
)