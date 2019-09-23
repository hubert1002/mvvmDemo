package com.example.testnewide.net

import android.util.Log
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import com.example.testnewide.net.service.GitHubApiBean
import com.example.testnewide.net.service.GitHubService
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class NetTest {
    fun test() {
        val gitHubService = RetrofitFactory.instance.retrofit.create(GitHubService::class.java)
        //调用指定方法
        val gitHubBeanCall = gitHubService.listGitHubApis()
        gitHubBeanCall.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.e("test1111", "onFailure" + t);
                t.printStackTrace()

            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.e("test1111", "onResponse" + response);
            }

        })
        gitHubService.listGitHubApis2().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnNext(object : Consumer<Response<Any>> {
                override fun accept(t: Response<Any>) {
                    Log.e("test1111", "accept" + t.body())
                }

            }).doOnError(object : Consumer<Throwable> {
                override fun accept(t: Throwable) {
                    Log.e("test1111", "doOnError" + t)
                    t.printStackTrace()
                }
            }).subscribe()


        gitHubService.listGitHubApis2().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ResultObserver<Any>() {
                override fun onSuccess(result: Any?) {
                    Log.e("test1111", "onSuccess" + result)
                }

                override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                    Log.e("test1111", "onFailure222" + e + isNetWorkError)
                }

            })

        testRX()
    }

    fun testRX() {
        Observable.just("aaaaa","adfasdfasdf")
            .map { t ->
                Thread.sleep(5000)
                t.length
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { t -> Log.e("test1111","accept "+t) }
            .subscribe (
                object : Observer<Int> {
                    override fun onNext(t: Int) {
                        Log.e("test", "onNext" + t)
                    }

                    override fun onComplete() {
                        Log.e("test", "onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.e("test", "onSubscribe")
                    }
                    override fun onError(e: Throwable) {
                        Log.e("test", "onError" + e)
                    }
                }
            )


        Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(e: ObservableEmitter<String>) {
                val gitHubService = RetrofitFactory.instance.retrofit.create(GitHubService::class.java)
                //调用指定方法
                val gitHubBeanCall = gitHubService.listGitHubApis()
                gitHubBeanCall.enqueue(object : Callback<Any> {
                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        Log.e("test1111", "11111onFailure" + t);
                        t.printStackTrace()
                        e.onError(t)
                    }

                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        Log.e("test1111", "11111onResponse" + response);
                        e.onNext("success")
                        e.onComplete()
                    }

                })
            }
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { t -> Log.e("test1111","11111accept "+t) }.subscribe()


    }

}