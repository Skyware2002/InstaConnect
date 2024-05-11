package com.skyware.instaconnect.post

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.skyware.instaconnect.HomeActivity
import com.skyware.instaconnect.Models.Post
import com.skyware.instaconnect.databinding.ActivityPostBinding
import com.skyware.instaconnect.utils.POST
import com.skyware.instaconnect.utils.POST_FOLDER
import com.skyware.instaconnect.utils.USER_NODE
import com.skyware.instaconnect.utils.uploadImage
import com.squareup.picasso.Picasso

class PostActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    var imageUrl: String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            //binding.selectImage.setImageURI(uri)
            uploadImage(uri, POST_FOLDER) { url ->
                if (url != null) {
                    //binding.selectImage.setImageURI(uri)
                    Picasso.get().load(url).into(binding.selectImage);
                    imageUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@PostActivity,HomeActivity::class.java))
            finish()
        }

        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@PostActivity,HomeActivity::class.java))
            finish()
        }

        binding.postButton.setOnClickListener {

            Firebase.firestore.collection(USER_NODE).document().get()
                .addOnSuccessListener {


                //var user = it.toObject<User>()!!
                val post: Post = Post(
                    postUrl = imageUrl!!,
                    caption = binding.caption.editableText.toString(),
                    uid = Firebase.auth.currentUser!!.uid,
                    time = System.currentTimeMillis().toString()
                )

                Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document()
                        .set(post)
                        .addOnSuccessListener {
                            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                            finish()
                        }
                }
            }
        }

    }
}