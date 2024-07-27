package com.faizuas.locationfinder

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.faizuas.locationfinder.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.loginBtn.setOnClickListener {
            loginUser()
        }

        binding.move.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Menampilkan pesan konfirmasi
                    Toast.makeText(this, "Anda berhasil login", Toast.LENGTH_SHORT).show()

                    // Arahkan ke MapActivity
                    startActivity(Intent(this, MapActivity::class.java))
                    finish()  // Menutup LoginActivity
                } else {
                    // Menampilkan pesan error jika login gagal
                    task.exception?.let {
                        Toast.makeText(this, "Login gagal: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
