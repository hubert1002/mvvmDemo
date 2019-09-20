package com.example.testnewide.chat.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import javax.inject.Scope
import kotlin.reflect.KClass

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)


@Scope
@kotlin.annotation.MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

