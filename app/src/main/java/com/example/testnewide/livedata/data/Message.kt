package com.example.testnewide.livedata.data

import androidx.room.*
import java.util.*

@Entity(
    tableName = "tb_messages",
    foreignKeys = [ForeignKey(entity = Contact::class, parentColumns = ["contactId"], childColumns = ["contactId"])],
    indices = [Index("mid"),Index("contactId")]
)
data class Message(

    val mid: String,
    val contactId: String,
    val msg:String,
    val date: Calendar = Calendar.getInstance()
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}