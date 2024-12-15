package com.example.uas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uas.admin.MainActivity
import com.example.uas.databinding.ActivityLoginBinding
import com.example.uas.user.UserMainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefManager = PrefManager.getInstance(this)

        with(binding) {
            btnLogin.setOnClickListener {
                val username = edtUsername.text.toString()
                val password = edtPassword.text.toString()

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Mohon isi semua data", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (isValidUsernamePassword()) {
                        prefManager.setLoggedIn(true)
                        checkLoginStatus(username, password)
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Username atau password salah",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            txtRegister.setOnClickListener {
                startActivity(
                    Intent(this@LoginActivity, RegisterActivity::class.java)
                )
            }
        }
    }

    private fun isValidUsernamePassword(): Boolean {
        val username = prefManager.getUsername()
        val password = prefManager.getPassword()
        val inputUsername = binding.edtUsername.text.toString()
        val inputPassword = binding.edtPassword.text.toString()
        return username == inputUsername && password == inputPassword
    }

    private fun checkLoginStatus(username: String, password: String) {
        if (username == "admin" && password == "asd") {
            Toast.makeText(
                this@LoginActivity,
                "Login berhasil sebagai admin",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(
                Intent(this@LoginActivity, MainActivity::class.java)
            )
        } else {
            Toast.makeText(
                this@LoginActivity,
                "Login berhasil sebagai user",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(
                Intent(this@LoginActivity, UserMainActivity::class.java)
            )
        }
        finish()
    }
}
