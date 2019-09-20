package com.example.testnewide.testdagger

import android.app.Application
import android.content.Context
import android.util.Log
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

public class AObject{

    fun eat(){
        Log.e("test", "eat")
    }
}
@Module
public class AModule{

    @Provides
    fun providerAObject():AObject{
        return AObject()
    }
}

@Component(modules = [AModule::class])
public interface AComponent {

    @Component.Builder
    interface Builder {
        //@BindsInstance注解的作用，只能在 Component.Builder 中使用
        //创建 Component 的时候绑定依赖实例
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AComponent

    }


    fun inject(caller:TestDagger)
}

class TestDagger{
    @Inject
    lateinit var a:AObject

    fun testDagger(){
        DaggerAComponent.builder().build().inject(this)
        a.eat()
    }
}