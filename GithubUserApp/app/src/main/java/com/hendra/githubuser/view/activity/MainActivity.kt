package com.hendra.githubuser.view.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.hendra.githubuser.R
import com.hendra.githubuser.adapter.SearchViewAdapter
import com.hendra.githubuser.viewmodel.SearchViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var searchViewAdapter: SearchViewAdapter
    private lateinit var searchViewModel: SearchViewModel

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        searchView(menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.changeLanguage) {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            return true
        } else {
            return true
        }
    }

    private fun searchView(menu: Menu) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchableInfo)
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // when search icon is pressed
            override fun onQueryTextSubmit(query: String): Boolean {
                showToast(this@MainActivity, query)
                return true
            }

            // when text changed
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}