package com.example.testnewide.livedata.data

import androidx.room.Embedded
import androidx.room.Relation

class ContactWithThread {

    @Embedded
    lateinit var contact: Contact

    @Relation(parentColumn = "contactId", entityColumn = "contactId")
    var threads: List<Thread> = arrayListOf()
}


class ContactWithMsg {

    @Embedded
    lateinit var contact: Contact

    @Relation(parentColumn = "contactId", entityColumn = "contactId")
    var messages: List<Message> = arrayListOf()
}