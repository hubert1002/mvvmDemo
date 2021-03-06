package com.example.testnewide.chat.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testnewide.chat.viewmodel.ContactListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(TestVM::class)
    abstract fun bindTestVM(vm: TestVM): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(ContactListViewModel::class)
    abstract fun bindContactListNewViewModel(vm: ContactListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: APPViewModelFactory): ViewModelProvider.Factory
}
