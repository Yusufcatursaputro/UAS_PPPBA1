package com.example.uas.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uas.AnimeAdapter
import com.example.uas.LoginActivity
import com.example.uas.PrefManager
import com.example.uas.databinding.ActivityMainBinding
import com.example.uas.model.Data
import com.example.uas.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: PrefManager
    private lateinit var animeAdapter: AnimeAdapter
    private val animeList = mutableListOf<Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager.getInstance(this)

        // Cek login status
        checkLoginStatus()

        // Setup tombol
        setupButtons()

        // Setup RecyclerView dan Adapter
        setupRecyclerView()

        // Fetch data dari API
        fetchAnimeData()
    }

    private fun setupButtons() {
        with(binding) {
            btnAdd.setOnClickListener {
                startActivity(Intent(this@MainActivity, AdminTambahActivity::class.java))
            }

            btnLogout.setOnClickListener {
                prefManager.setLoggedIn(false)
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun setupRecyclerView() {
        animeAdapter = AnimeAdapter(
            animeList,
            onDelete = { id, position ->
                deleteAnime(id, position)
            },
            onEdit = { anime ->
                val intent = Intent(this, AdminDetailActivity::class.java).apply {
                    putExtra("id", anime.id)
                    putExtra("judul", anime.judul)
                    putExtra("episode", anime.episode)
                    putExtra("date", anime.date)
                    putExtra("genre", anime.genre)
                    putExtra("image", anime.image)
                    putExtra("sinopsis", anime.sinopsis)
                }
                startActivity(intent)
            },
            isAdmin = true
        )
        binding.daftarAnime.layoutManager = LinearLayoutManager(this)
        binding.daftarAnime.adapter = animeAdapter
    }
    private fun fetchAnimeData() {
        val apiService = ApiClient.getInstance()
        apiService.getAllUsers().enqueue(object : Callback<List<Data>> {
            override fun onResponse(call: Call<List<Data>>, response: Response<List<Data>>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null) {
                        animeList.clear()
                        animeList.addAll(responseData)
                        animeAdapter.notifyDataSetChanged()
                    } else {
                        Log.d("MainActivity", "Data kosong")
                    }
                } else {
                    Log.e("MainActivity", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Data>>, t: Throwable) {
                Log.e("MainActivity", "Failure: ${t.message}")
            }
        })
    }

    private fun deleteAnime(id: String, position: Int) {
        val apiService = ApiClient.getInstance()
        apiService.deleteAnime(id.toString()).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    animeList.removeAt(position)
                    animeAdapter.notifyItemRemoved(position)
                } else {
                    Log.e("MainActivity", "Gagal menghapus data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("MainActivity", "Error saat menghapus data: ${t.message}")
            }
        })
    }
    fun checkLoginStatus() {
        val isLoggedIn = prefManager.isLoggedIn()
        if (!isLoggedIn) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }
}