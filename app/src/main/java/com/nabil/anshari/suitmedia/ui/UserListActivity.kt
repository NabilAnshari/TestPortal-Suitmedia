package com.nabil.anshari.suitmedia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nabil.anshari.suitmedia.adapter.Adapter
import com.nabil.anshari.suitmedia.config.ApiConfig
import com.nabil.anshari.suitmedia.databinding.ActivityUserListBinding
import com.nabil.anshari.suitmedia.response.ResponseApi
import com.nabil.anshari.suitmedia.response.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class UserListActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewBinding: ActivityUserListBinding
    private lateinit var userAdapter: Adapter

    companion object {
        private const val TITLE = "User List Screen"
        private var isLoading = false
        private var currentPage = 1
        private var maxPages = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityUserListBinding.inflate(layoutInflater).apply { setContentView(root) }

        setupActionBar()
        initializeUserRecyclerView()
        loadUsers(initialLoad = true)
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = TITLE
        }
    }

    private fun initializeUserRecyclerView() {
        userAdapter = Adapter().apply {
            setClickCallback(object : Adapter.OnItemClickCallback {
                override fun onItemClicked(data: UserData) {
                    navigateToUserDetails(data)
                }
            })
        }

        viewBinding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@UserListActivity)
            adapter = userAdapter
            setHasFixedSize(true)
        }

        viewBinding.refreshScreen.setOnRefreshListener(this)
    }

    private fun navigateToUserDetails(userData: UserData) {
        Intent(this, SelectUserActivity::class.java).also {
            it.putExtra(SelectUserActivity.EXTRA_NAME, userData.firstName)
            startActivity(it)
        }
    }

    private fun loadUsers(initialLoad: Boolean) {
        if (initialLoad) isLoading = true
        showLoadingIndicator(isVisible = !initialLoad)

        Handler().postDelayed({
            fetchUsersFromApi()
        }, 3000)
    }

    private fun fetchUsersFromApi() {
        val queryParameters = hashMapOf("page" to currentPage.toString())
        ApiConfig.getApiService().getUser(queryParameters).enqueue(apiResponseCallback())
    }

    private fun apiResponseCallback() = object : Callback<ResponseApi> {
        override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
            if (response.isSuccessful) {
                handleSuccessfulResponse(response)
            }
        }

        override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
            handleError(t)
        }
    }

    private fun handleSuccessfulResponse(response: Response<ResponseApi>) {
        maxPages = response.body()?.totalPages ?: 1
        val users = response.body()?.data.orEmpty()
        userAdapter.setList(users as ArrayList<UserData>)

        showLoadingIndicator(isVisible = false)
        viewBinding.refreshScreen.isRefreshing = false
    }

    private fun handleError(throwable: Throwable) {
        Log.e(TITLE, "Error fetching users: ${throwable.message}")
        Toast.makeText(this, "Failed to connect. Please try again.", Toast.LENGTH_SHORT).show()
        showLoadingIndicator(isVisible = false)
        viewBinding.refreshScreen.isRefreshing = false
    }

    private fun showLoadingIndicator(isVisible: Boolean) {
        viewBinding.progressbar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun onRefresh() {
        userAdapter.clearUsers()
        currentPage = 1
        loadUsers(initialLoad = false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
