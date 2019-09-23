package com.example.testnewide.net.service

import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST




interface GitHubService {
    @GET("/")
    fun listGitHubApis(): Call<Any>

    @GET("/")
    fun listGitHubApis2(): Observable<Response<Any>>
}

data class GitHubApiBean(private val key:String,private val value:String)
