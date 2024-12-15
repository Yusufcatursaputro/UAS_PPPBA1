package com.example.uas.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uas.AnimeAdapter
import com.example.uas.LoginActivity
import com.example.uas.PrefManager
import com.example.uas.databinding.UserMainBinding
import com.example.uas.model.Data
import com.example.uas.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserMainActivity : AppCompatActivity() {
    private lateinit var binding: UserMainBinding
    private lateinit var prefManager: PrefManager
    private lateinit var animeAdapter: AnimeAdapter
    private val animeList = mutableListOf<Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = UserMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager.getInstance(this)
        checkLoginStatus()

        // Inisialisasi Adapter
        animeAdapter = AnimeAdapter(
            listAnime = animeList,
            onDelete = { id, position ->
                Log.d("UserMainActivity", "Delete not implemented for user")
            },
            onEdit = { anime ->
                Log.d("UserMainActivity", "Edit not implemented for user")
            },
            isAdmin = false
        )

        /// Setup RecyclerView
        binding.daftarAnime.apply {
            layoutManager = LinearLayoutManager(this@UserMainActivity)
            adapter = animeAdapter
        }

        // Setup Bottom Navbar Navigation
        setupBottomNavbar()

        // Setup Logout Button
        binding.btnLogout.setOnClickListener {
            prefManager.setLoggedIn(false)
            startActivity(Intent(this@UserMainActivity, LoginActivity::class.java))
            finish()
        }

        // Fetch data dari API
        fetchAnimeData()
    }

    private fun setupBottomNavbar() {
        binding.apply {
            // Navigasi Home (Tetap di halaman ini)
            navHome.setOnClickListener {
                Log.d("UserMainActivity", "Already in Home")
            }

            // Navigasi ke UserBookmark
            navBookmark.setOnClickListener {
                val intent = Intent(this@UserMainActivity, UserBookmarkActivity::class.java)
                startActivity(intent)
            }

            // Navigasi ke UserProfile
            navProfile.setOnClickListener {
                val intent = Intent(this@UserMainActivity, UserProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun fetchAnimeData() {
        val apiService = ApiClient.getInstance()
        apiService.getAllUsers().enqueue(object : Callback<List<Data>> {
            override fun onResponse(call: Call<List<Data>>, response: Response<List<Data>>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null) {
                        animeList.clear() // Hapus data lama
                        animeList.addAll(responseData) // Tambahkan data baru
                        animeAdapter.notifyDataSetChanged() // Beri tahu adapter
                        Log.d("UserMainActivity", "Data berhasil dimuat: ${responseData.size} item")
                    } else {
                        Log.d("UserMainActivity", "Data kosong")
                    }
                } else {
                    Log.e("UserMainActivity", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Data>>, t: Throwable) {
                Log.e("UserMainActivity", "Failure: ${t.message}")
            }
        })
    }

    fun checkLoginStatus() {
        val isLoggedIn = prefManager.isLoggedIn()
        if (!isLoggedIn) {
            startActivity(Intent(this@UserMainActivity, LoginActivity::class.java))
            finish()
        }
    }
}