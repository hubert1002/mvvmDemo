package com.example.testnewide.chat.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testnewide.TestApp
import com.example.testnewide.livedata.ChatData
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AppModule::class])
interface AppComponent {
    /**
     * 通过 @Component.Builder 增加builder方法，提供Application 注入方法。
     */
    @Component.Builder
    interface Builder {
        //@BindsInstance注解的作用，只能在 Component.Builder 中使用
        //创建 Component 的时候绑定依赖实例
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun tag(tag: String): Builder

        fun build(): AppComponent
    }

    fun inject(app: TestApp)
}


@Module(includes = [ViewModelModule::class])
class AppModule(private var app:Application) {
//    @Singleton
//    @Provides
//    fun providerRetrofit(): Retrofit {
//        return RetrofitFactory.instance.retrofit
//    }
//
//    @Singleton
//    @Provides
//    fun providerRealmFactory(): RealmFactory {
//        return RealmFactory.instance
//    }
//
//
//    @Provides
//    fun tag():String{
//        return app.packageName
//    }

//    @Provides
//    fun providerApplication():Application{
//        return app
//    }

}




class APPViewModelFactory @Inject constructor(private val creators:Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        //通过class找到相应ViewModel的Provider
        val creator = creators[modelClass]?:creators.entries.firstOrNull{
            modelClass.isAssignableFrom(it.key)
        }?.value?:throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T //通过get()方法获取到ViewModel
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
