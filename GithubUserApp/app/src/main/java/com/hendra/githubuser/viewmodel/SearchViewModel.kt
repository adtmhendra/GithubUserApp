package com.hendra.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hendra.githubuser.model.ItemsItem
import com.hendra.githubuser.model.ResponseUser
import com.hendra.githubuser.network.ApiConfig
import com.hendra.githubuser.view.activity.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel() : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<ItemsItem>>()

    fun setUser(username: String) {
        val dataItems = ArrayList<ItemsItem>()

        val client = ApiConfig.getApiService().getListUsers(username)

        client.enqueue(object : Callback<ResponseUser> {

            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                Log.d(MainActivity.TAG, "onResponse: Start...")
                val dataArray = response.body()?.items as List<ItemsItem>
                Log.d(MainActivity.TAG, dataArray.toString())
                try {
                    for (data in dataArray) {
                        dataItems.add(data)
                    }
                    listUsers.postValue(dataItems)
                    Log.d(MainActivity.TAG, "onResponse: Finished...")
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

    fun getUser(): LiveData<ArrayList<ItemsItem>> {
        return listUsers
    }
}