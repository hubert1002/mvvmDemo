package com.example.testnewide.chat.di

import com.example.testnewide.chat.ChatActivity
import com.example.testnewide.chat.ContactFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindModule{
    @ActivityScope
    @ContributesAndroidInjector(modules = [ChatFragmentBindModule::class])
    abstract fun startChatActivityInjector(): ChatActivity
    //---其他activity
}

@Module
abstract class ChatFragmentBindModule{
    @ContributesAndroidInjector
    abstract fun contributeContactFragment(): ContactFragment
    //---其他fragment
}