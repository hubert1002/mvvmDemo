package com.example.testnewide.utility

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testnewide.livedata.data.ContactRepo
import com.google.samples.apps.sunflower.data.GardenPlantingRepository
import com.google.samples.apps.sunflower.utilities.InjectorUtils
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModel
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModelFactory


//
//class ContactViewModelFactory(
//    private val repository: ContactRepo
//) : ViewModelProvider.NewInstanceFactory() {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return GardenPlantingListViewModel(repository) as T
//    }
//}
//
//fun provideGardenPlantingListViewModelFactory(
//    context: Context
//): GardenPlantingListViewModelFactory {
//    val repository = InjectorUtils.getGardenPlantingRepository(context)
//    return GardenPlantingListViewModelFactory(repository)
//}