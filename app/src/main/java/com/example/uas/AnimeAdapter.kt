package com.example.uas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uas.databinding.ListAnimeBinding
import com.example.uas.model.Data
import android.content.Intent
import android.view.View
import com.example.uas.admin.AdminDetailActivity

class AnimeAdapter(
    private val listAnime: List<Data>?,
    private val onDelete: (id: String, position: Int) -> Unit,
    private val onEdit: (anime: Data) -> Unit,
    private val isAdmin: Boolean
    ) : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    // ViewHolder untuk RecyclerView
    inner class AnimeViewHolder(val binding: ListAnimeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = ListAnimeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = listAnime?.get(position) ?: return

        val binding = holder.binding

        // Mengisi data ke View menggunakan Binding
        binding.apply {
            Glide.with(root.context)
                .load(anime.image) // Load gambar dari URL
                .into(animeImage)

            animeTitle.text = anime.judul
            animeGenre.text = anime.genre

            // Menangani klik item
            root.setOnClickListener {
                val intent = Intent(root.context, AdminDetailActivity::class.java)
                populateIntent(intent, anime)
                root.context.startActivity(intent)
            }

            // Sembunyikan tombol Edit dan Delete jika bukan admin
            if (!isAdmin) {
                editIcon.visibility = View.GONE
                deleteIcon.visibility = View.GONE
            }

            // Edit
            editIcon.setOnClickListener {
                onEdit(anime)
            }

            // Delete
            deleteIcon.setOnClickListener {
                onDelete(anime.id, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return listAnime!!.size
    }
    // Fungsi untuk mengisi intent dengan data anime
    private fun populateIntent(intent: Intent, anime: Data) {
        intent.apply {
            putExtra("judul", anime.judul)
            putExtra("episode", anime.episode)
            putExtra("date", anime.date)
            putExtra("genre", anime.genre)
            putExtra("image", anime.image)
            putExtra("sinopsis", anime.sinopsis)
        }
    }
}
