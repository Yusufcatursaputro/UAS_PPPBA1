package com.example.uas.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uas.LoginActivity
import com.example.uas.databinding.AdminTambahBinding
import com.example.uas.model.Data
import com.example.uas.network.ApiClient
import com.example.uas.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminTambahActivity : AppCompatActivity() {

    private lateinit var binding: AdminTambahBinding
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminTambahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi API Service
        apiService = ApiClient.getInstance()

        // Tombol Kembali
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this@AdminTambahActivity, MainActivity::class.java))
        }
        
        // Event Listener untuk Tombol Simpan
        binding.simpanButton.setOnClickListener {
            saveAnime()
        }
    }

    private fun saveAnime() {
        // Mengambil data dari form
        val judul = binding.tambahJudul.text.toString().trim()
        val jumlahEpisode = binding.tambahEps.text.toString().trim()
        val tanggalRilis = binding.tambahTanggal.text.toString().trim()
        val genre = binding.tambahGenre.text.toString().trim()
        val image = binding.tambahImage.text.toString().trim()
        val sinopsis = binding.tambahSinopsis.text.toString().trim()

        // Validasi input
        if (judul.isEmpty() || jumlahEpisode.isEmpty() || tanggalRilis.isEmpty() ||
            genre.isEmpty() || image.isEmpty() || sinopsis.isEmpty()
        ) {
            Toast.makeText(this, "Semua data wajib diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        // Membuat objek data untuk dikirim ke API
        val newAnime = Data(
            id = null.toString(),
            judul = judul,
            episode = jumlahEpisode,
            date = tanggalRilis,
            genre = genre,
            image = image,
            sinopsis = sinopsis
        )

        // Memanggil API untuk menyimpan data
        apiService.addAnime(newAnime).enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AdminTambahActivity, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                    finish() // Kembali ke aktivitas sebelumnya
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(this@AdminTambahActivity, "Gagal menambahkan data: ${response.code()}", Toast.LENGTH_SHORT).show()
                    Log.e("AdminTambahActivity", "Error Code: ${response.code()}, Error Body: $errorBody")
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Toast.makeText(this@AdminTambahActivity, "Gagal menyimpan data: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("AdminTambahActivity", "Failure: ${t.message}")
            }
        })
    }
}
