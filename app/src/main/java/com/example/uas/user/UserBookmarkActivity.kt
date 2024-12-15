package com.example.uas.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.uas.PrefManager
import com.example.uas.databinding.UserBookmarkBinding

class UserBookmarkActivity : AppCompatActivity() {
    private lateinit var binding: UserBookmarkBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefManager = PrefManager.getInstance(this)
        with(binding) {
        }
        // Setup Bottom Navbar Navigation
        setupBottomNavbar()
    }
    private fun setupBottomNavbar() {
        binding.apply {
            // Navigasi Home
            navHome.setOnClickListener {
                val intent = Intent(this@UserBookmarkActivity, UserMainActivity::class.java)
                startActivity(intent)
            }

            // Navigasi ke UserBookmark
            navBookmark.setOnClickListener {
                Log.d("UserBookmarkActivity", "Already in Bookmark")
            }

            // Navigasi ke UserProfile
            navProfile.setOnClickListener {
                val intent = Intent(this@UserBookmarkActivity, UserProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }
}