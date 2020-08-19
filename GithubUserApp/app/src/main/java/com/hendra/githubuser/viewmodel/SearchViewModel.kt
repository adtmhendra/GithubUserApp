package com.hendra.githubuser.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hendra.githubuser.model.ItemsItem
import com.hendra.githubuser.model.ResponseUser
import com.hendra.githubuser.network.ApiConfig
import com.hendra.githubuser.view.activity.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    val listUsers = MutableLiveData<ItemsItem>()

    fun setUser(username: String) {
        val client = ApiConfig.getApiService().getListUsers(username)

        client.enqueue(object : Callback<ResponseUser> {

            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                Log.d(MainActivity.TAG, "onResponse: Start...")
                val dataArray = response.body()?.items as List<ItemsItem?>
                Log.d(MainActivity.TAG, dataArray.toString())
                try {
                    for (data in dataArray) {
                        listUsers.postValue(data)
                    }
                } catch (e: Exception) {
                    Log.d(MainActivity.TAG, "onResponse: Failed...")
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Log.d(MainActivity.TAG, "onFailure: Failed...")
                t.printStackTrace()
            }

        })
    }

    fun getUser(): LiveData<ItemsItem> = listUsers
}