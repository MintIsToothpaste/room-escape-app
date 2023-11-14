package com.chanwoong.myroomescapeapp

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chanwoong.myroomescapeapp.databinding.ActivityCafeReviewPostingBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CafeReviewPostingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCafeReviewPostingBinding
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafeReviewPostingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.postButton.setOnClickListener {
            val user = Firebase.auth.currentUser

            val docRef = user?.let { it1 -> db.collection("users").document(it1.uid) }
            docRef?.get()?.addOnSuccessListener { document ->
                if (document != null) {
                    val review = binding.postEditText.text.toString().trim()
                    val whoPosted = "${Firebase.auth.currentUser?.uid}"
                    val rating = binding.ratingBar2.rating.toString().trim()

                    val itemMap = hashMapOf(
                        "whoPosted" to whoPosted,
                        "review" to review,
                        "rating" to rating
                    )

                    db.collection("review").document()
                        .set(itemMap)
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