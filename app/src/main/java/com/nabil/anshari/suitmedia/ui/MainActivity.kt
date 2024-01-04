package com.nabil.anshari.suitmedia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nabil.anshari.suitmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupContent()

    }

    private fun setupContent() {
        val palindrome = binding.editPalindrome.text
        val name = binding.editName.text

        binding.btnCheck.setOnClickListener {
            var isPalindrome = true
            if(palindrome.isEmpty()){
                binding.editPalindrome.error ="Must be filled!"
                isPalindrome = false
            }

            val textLength = palindrome.length
            var i = 0
            while(i<textLength/2){
                i++
                if(palindrome[i] != palindrome[textLength-1-i]){
                    isPalindrome = false
                    break
                }
            }
            if(isPalindrome){
                Toast.makeText(this, "Palindrome", Toast.LENGTH_SHORT).show()
            }else if(!isPalindrome && palindrome.isNotEmpty()) {
                Toast.makeText(this, "Not Palindrome", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnNext.setOnClickListener {
            val moveToSecond = Intent(this, SelectUserActivity::class.java)
            moveToSecond.putExtra(SelectUserActivity.EXTRA_NAME,name.toString())
//            val coba = intent.putExtra(SecondActivity.EXTRA_NAME,name.toString())
//            Log.d(TAG, "$coba")
            if(name.isEmpty()){
                binding.editName.error = "Must be filled!"
            }else startActivity(moveToSecond)
        }
    }
}