package com.skyware.instaconnect.post

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.skyware.instaconnect.HomeActivity
import com.skyware.instaconnect.Models.Reel
import com.skyware.instaconnect.Models.User
import com.skyware.instaconnect.databinding.ActivityReelsBinding
import com.skyware.instaconnect.utils.REEL
import com.skyware.instaconnect.utils.REEL_FOLDER
import com.skyware.instaconnect.utils.USER_NODE
import com.skyware.instaconnect.utils.uploadVideo

class ReelsActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityReelsBinding.inflate(layoutInflater)
    }

    private lateinit var VideoUrl:String
    //var progressDialog=ProgressDialog(this)
    lateinit var progressDialog:ProgressDialog
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadVideo(uri, REEL_FOLDER, progressDialog) { url ->
                if (url != null) {

                    VideoUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        progressDialog=ProgressDialog(this)

        binding.selectReel.setOnClickListener {
            launcher.launch("video/*")
        }

        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }


        binding.postButton.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                var user:User = it.toObject<User>()!!

                var reel: Reel = Reel(VideoUrl!!, binding.caption.editableText.toString(),user.image!!)

                Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ REEL).document().set(reel)
                        .addOnSuccessListener {
                            startActivity(Intent(this@ReelsActivity,HomeActivity::class.java))
                            finish()
                        }
                }
            }
        }


    }
}