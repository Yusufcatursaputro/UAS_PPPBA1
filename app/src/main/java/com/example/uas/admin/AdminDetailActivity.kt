package com.example.uas.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.uas.databinding.AdminDetailBinding

class AdminDetailActivity : AppCompatActivity() {

    private lateinit var binding: AdminDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AdminDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan data dari Intent
        val judul = intent.getStringExtra("judul")
        val episode = intent.getStringExtra("episode")
        val date = intent.getStringExtra("date")
        val genre = intent.getStringExtra("genre")
        val image = intent.getStringExtra("image")
        val sinopsis = intent.getStringExtra("sinopsis")

        // Menampilkan data di UI
        binding.detailJudul.text = judul
        binding.detailEps.text = "$episode Episode"
        binding.detailTanggal.text = date
        binding.detailGenre.text = genre
        binding.detailSinopsis.text = sinopsis
        Glide.with(this).load(image).into(binding.imageDetail)

        // Tombol Kembali
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}