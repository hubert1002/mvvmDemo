package com.example.testnewide.net

import android.accounts.NetworkErrorException
import android.graphics.pdf.PdfDocument
import com.example.testnewide.BuildConfig
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class RetrofitFactory private constructor(){
    val retrofit: Retrofit
    init {
        //打印请求log
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        val mOkHttpClient = OkHttpClient.Builder()
            .connectTimeout( 20 * 1000L, TimeUnit.SECONDS)
            .addInterceptor(logging)
//            .addInterceptor(headerInterceptor())
//            .addInterceptor(PageInfoInterceptor())
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(mOkHttpClient)
            .build()
    }

    companion object {

        @Volatile
        private var mRetrofitFactory: RetrofitFactory? = null

        val instance: RetrofitFactory
            get() {
                if (mRetrofitFactory == null) {
                    synchronized(RetrofitFactory::class.java) {
                        if (mRetrofitFactory == null)
                            mRetrofitFactory = RetrofitFactory()
                    }

                }
                return mRetrofitFactory!!
            }

        fun <T> createService(service: Class<T>): T {
            return instance.retrofit.create(service)
        }
//
//        /**
//         * 执行请求返回结果
//         */
//        fun <T> executeResult(observable: Observable<Response<T>>, subscriber: ResultObserver<T>) {
//            observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber)
//        }

    }
}
abstract class TestObserver<T> : Observer<T>{
    override fun onSubscribe(d: Disposable) {
    }
}


abstract class ResultObserver<T> : Observer<Response<T>> {

    override fun onSubscribe(d: Disposable) {
        if (!d.isDisposed) {
            onRequestStart()
        }
    }

    override fun onNext(reposnse: Response<T>) {
        onPageInfo(reposnse)
        onRequestEnd()
        if (reposnse.isSuccessful) {
            try {
                onSuccess(reposnse.body())
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            try {
                onInnerCodeError(reposnse.code(), reposnse.message())
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onError(e: Throwable) {
        onRequestEnd()
        try {
            if (e is ConnectException
                || e is TimeoutException
                || e is NetworkErrorException
                || e is UnknownHostException
            ) {
                onFailure(e, true)
            } else {
                onFailure(e, false)
            }
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

    }

    override fun onComplete() {}

    open fun onInnerCodeError(code: Int, message: String) {
        onCodeError(code, message)
    }

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    @Throws(Exception::class)
    open fun onCodeError(code: Int, message: String) {

    }

    open fun onRequestStart() {

    }

    open fun onRequestEnd() {

    }

    open fun onPageInfo(first: Int, current: Int, last: Int) {

    }


    fun onPageInfo(response: Response<T>) {
//        val pageString = response.headers().get("page_info")
//        if (pageString != null) {
//            val pageInfo = GsonUtils.parserJsonToBean(pageString, PdfDocument.PageInfo::class.java)
//            onPageInfo(pageInfo.first, pageInfo.next - 1, pageInfo.last)
//        }
    }

    /**
     * 返回成功
     *
     * @param result
     * @throws Exception
     */
    @Throws(Exception::class)
    abstract fun onSuccess(result: T?)

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    @Throws(Exception::class)
    abstract fun onFailure(e: Throwable, isNetWorkError: Boolean)

}
