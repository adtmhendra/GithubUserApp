package com.hendra.githubuser.view.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hendra.githubuser.R
import com.hendra.githubuser.adapter.SearchViewAdapter
import com.hendra.githubuser.model.ItemsItem
import com.hendra.githubuser.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: SearchViewAdapter
    private lateinit var searchViewModel: SearchViewModel
    private val listUsers = ArrayList<ItemsItem>()

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = SearchViewAdapter(listUsers)
        rvSearchView.layoutManager = LinearLayoutManager(this)
        rvSearchView.adapter = adapter

        bindWithViewModel()
        getItems()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchableInfo)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // when search icon is pressed
            override fun onQueryTextSubmit(query: String): Boolean {
                showProgressBar(true)
                showEmptyData(false)
                searchViewModel.setUser(query)
                return true
            }

            // when text changed
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.changeLanguage) {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            return true
        } else {
            return true
        }
    }

    private fun bindWithViewModel() {
        searchViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SearchViewModel::class.java)
    }

    private fun getItems() {
        searchViewModel.getUser().observe(this, Observer {
            if (it != null) {
                showEmptyData(false)
                listUsers.addAll(it)
                showProgressBar(false)
            } else showEmptyData(true)
        })
    }

    private fun showProgressBar(boolean: Boolean) {
        if (boolean) progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.INVISIBLE
    }

    private fun showEmptyData(boolean: Boolean) {
        if (boolean) {
            searchData.visibility = View.VISIBLE
            tvTitle.visibility = View.VISIBLE
            tvDescription.visibility = View.VISIBLE
        } else {
            searchData.visibility = View.INVISIBLE
            tvTitle.visibility = View.INVISIBLE
            tvDescription.visibility = View.INVISIBLE
        }
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}