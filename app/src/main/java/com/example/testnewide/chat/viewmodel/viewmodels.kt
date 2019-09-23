package com.example.testnewide.chat.viewmodel

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.example.testnewide.livedata.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


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


class ContactListViewModel @Inject constructor(private val repo: ContactRepo) : ViewModel() {

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
    var threadList : LiveData<List<ThreadWithMsgContact>> = threadRepo.getThreadInfo().map {item->
        item.filter {
            it.contacts.isNotEmpty()&& it.messages.isNotEmpty()
        }
    }
}


class ThreadInfo (private val threadInfo : ThreadWithMsgContact):ViewModel(){
    private val contact = checkNotNull(threadInfo.contacts[0])
    private val message = checkNotNull(threadInfo.messages[0])
    val msg = ObservableField<String>(message.msg)
    val imageUrl = ObservableField<String>(contact.imageUrl)
    val name = ObservableField<String>(contact.name)

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

    fun addMsgAndUpdateThread(contactId: String){
        viewModelScope.launch {
            var time = System.currentTimeMillis()
            val msg = Message(time.toString(),contactId,time.toString())
            msgRepo.insertItemAndUpdateThread(msg)
        }
    }
}

