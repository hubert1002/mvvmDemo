package com.example.testnewide.livedata.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tb_thread", indices = [Index("contactId")])
data class Thread(
    val contactId: String,
    val mid: String,
    @ColumnInfo(name = "plant_date") val plantDate: Calendar = Calendar.getInstance()
    ){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}