package com.skyware.instaconnect

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.skyware.instaconnect.Models.User
import com.skyware.instaconnect.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            if (binding.email.editableText?.toString()
                    .equals("") or binding.pass.editableText?.toString().equals("")
            ){
                Toast.makeText(
                    this@LoginActivity,
                    "Please fill All Details",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                var user = User(
                    binding.email.editableText?.toString(),
                    binding.pass.editableText?.toString()
                )

                Firebase.auth.signInWithEmailAndPassword(user.email!!, user.password!!)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                it.exception?.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}