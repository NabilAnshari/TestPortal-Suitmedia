package com.nabil.anshari.suitmedia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.nabil.anshari.suitmedia.R
import com.nabil.anshari.suitmedia.databinding.ActivitySelectUserBinding

@Suppress("DEPRECATION")
class SelectUserActivity :  AppCompatActivity() {
    private lateinit var binding: ActivitySelectUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_NAME)
        binding.tvUsername.text = username

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = SetupActivity

        setupContent()
    }

    private fun setupContent() {
        var selectedUser = binding.tvSelectedUser.isVisible
        binding.btnChoose.setOnClickListener {
            val moveToThird = Intent(this,UserListActivity::class.java)
            if (!selectedUser){
                startActivity(moveToThird)
            }else {
                Toast.makeText(this, "Must click name!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvUsername.setOnClickListener {
            binding.tvSelectedUser.visibility = View.VISIBLE
            binding.tvSelectedUser.text = SELECTED
            binding.tvUsername.setTextColor(ContextCompat.getColor(this, R.color.button_color))
            selectedUser = false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object{
        const val EXTRA_NAME = "extra_name"
        const val SELECTED = "Selected User Name"
        private var SetupActivity ="User Select Screen"
    }
}
