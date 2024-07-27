package com.faizuas.locationfinder

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.faizuas.locationfinder.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.registerBtn.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Menampilkan pesan konfirmasi
                    Toast.makeText(this, "Anda sudah melakukan registrasi", Toast.LENGTH_SHORT).show()

                    // Arahkan ke halaman login
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()  // Menutup RegisterActivity
                } else {
                    // Menampilkan pesan error jika registrasi gagal
                    task.exception?.let {
                        Toast.makeText(this, "Registrasi gagal: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
