package com.hendra.githubuser.network

import com.hendra.githubuser.model.ResponseUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getListUsers(@Query("q") username: String?) : Call<ResponseUser>
}