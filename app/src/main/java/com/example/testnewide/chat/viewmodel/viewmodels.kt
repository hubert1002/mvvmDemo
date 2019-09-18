package com.example.testnewide.chat.viewmodel

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.example.testnewide.livedata.data.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class ContactDetailViewModel (private val repo: ContactRepo, private val threadRepo: ThreadRepo, val id:String):ViewModel(){
    val contactInfo :LiveData<Contact>
    val hasThread :LiveData<Boolean>
    init {
        val gardenPlantingForPlant = threadRepo.getThreadsWithContact(id)
        hasThread = gardenPlantingForPlant.map { it != null }
        contactInfo = repo.getContact(id)
    }
    fun add2Thread(contactId:String) {
        viewModelScope.launch {
            val thread = com.example.testnewide.livedata.data.Thread(contactId,"")
            threadRepo.insertItem(thread)
        }
    }
}


class ContactListViewModel internal constructor(repo: ContactRepo) : ViewModel() {

    private val age = MutableLiveData<Int>().apply { value = NONE }

    val contacts: LiveData<List<Contact>> = age.switchMap {
        if (it == NONE) {
            repo.getContact()
        } else {
            repo.getContactWithAge(it)
        }
    }

    fun setAge(num: Int) {
        age.value = num
    }

    fun clearAge() {
        age.value = NONE
    }

    fun isFiltered() = age.value != NONE

    companion object {
        private const val NONE = -1
    }
}


class ThreadVM(private val threadRepo: ThreadRepo) :ViewModel(){
    var threadList : LiveData<List<ContactWithThread>> = threadRepo.getContactWithThread().map {item->
        item.filter {
            it.threads.isNotEmpty()
        }
    }
}

class MsgVM(private val msgRepo: MsgRepo,private val contactId:String) :ViewModel(){
    var msgList: LiveData<List<ContactWithMsg>> = msgRepo.getContactWithMsg(contactId)

    fun addMsg(contactId: String) {
        viewModelScope.launch {
            var time = System.currentTimeMillis()
            val msg = Message(time.toString(),contactId,time.toString())
            msgRepo.insertItem(msg)
        }
    }
}


class ThreadInfoVM (private val threadInfo : ContactWithThread):ViewModel(){
    private val contact = checkNotNull(threadInfo.contact)
    private val threadinfo = threadInfo.threads[0]
    private val dateFormat by lazy { SimpleDateFormat("MMM d, yyyy", Locale.US) }
    val wateringInterval = ObservableInt(contact.age)
    val imageUrl = ObservableField<String>(contact.imageUrl)
    val name = ObservableField<String>(contact.name)

}