package com.chanwoong.myroomescapeapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chanwoong.myroomescapeapp.databinding.ActivityReviewBinding
import com.chanwoong.myroomescapeapp.fragment.*
import com.chanwoong.myroomescapeapp.viewmodels.CafeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.postButton.setOnClickListener {
            val review = binding.postEditText.text.toString()
            val whoPosted = "${Firebase.auth.currentUser?.uid}"

            val itemMap = hashMapOf(
                "whoPosted" to whoPosted,
                "review" to review
            )
        }
    }


}