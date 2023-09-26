package com.chanwoong.myroomescapeapp

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chanwoong.myroomescapeapp.databinding.ActivityCommunityPostingBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CommunityPostingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityPostingBinding
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityPostingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.communityPostingButton.setOnClickListener {
            val user = Firebase.auth.currentUser

            val docRef = user?.let { it1 -> db.collection("users").document(it1.uid) }
            docRef?.get()?.addOnSuccessListener { document ->
                if (document != null) {
                    val nickname = document["nickname"]
                    val post = binding.postingEditText.text.toString().trim()
                    val title = binding.titleEditText.text.toString().trim()

                    val posting = hashMapOf(
                        "title" to title,
                        "post" to post,
                        "nickname" to nickname,
                    )

                    db.collection("posting").document(user.uid)
                        .set(posting)
                        .addOnSuccessListener {
                            Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!")
                            startActivity(
                                Intent(this, MainActivity::class.java)
                            )
                        }
                        .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
                } else {
                    Log.d(ContentValues.TAG, "No such document")
                }
            }?.addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }

        }

    }

}