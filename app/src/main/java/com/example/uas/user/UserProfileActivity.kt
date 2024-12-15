package com.example.uas.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.uas.PrefManager
import com.example.uas.databinding.UserProfileBinding

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: UserProfileBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefManager = PrefManager.getInstance(this)
        with(binding) {
            txtUsername.text = prefManager.getUsername()
        }
        // Setup Bottom Navbar Navigation
        setupBottomNavbar()
    }
    private fun setupBottomNavbar() {
        binding.apply {
            // Navigasi Home
            navHome.setOnClickListener {
                val intent = Intent(this@UserProfileActivity, UserMainActivity::class.java)
                startActivity(intent)
            }

            // Navigasi ke UserBookmark
            navBookmark.setOnClickListener {
                val intent = Intent(this@UserProfileActivity, UserBookmarkActivity::class.java)
                startActivity(intent)
            }

            // Navigasi ke UserProfile
            navProfile.setOnClickListener {
                Log.d("UserProfileActivity", "Already in Home")
            }
        }
    }

}