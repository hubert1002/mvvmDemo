package com.example.testnewide.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testnewide.livedata.data.ContactRepo
import com.example.testnewide.livedata.data.ThreadRepo

class ContactListViewModelFactory(private val repository: ContactRepo) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactListViewModel(repository) as T
    }
}


class ContactDetailViewModelFactory(private val repository: ContactRepo,private val threadRepo: ThreadRepo,private val id :String) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactDetailViewModel(repository,threadRepo,id) as T
    }
}

class ThreadListViewModelFactory(private val threadRepo: ThreadRepo) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ThreadVM(threadRepo) as T
    }
}