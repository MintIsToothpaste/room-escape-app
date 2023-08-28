package com.chanwoong.myroomescapeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chanwoong.myroomescapeapp.databinding.ActivityCafeDetailBinding

class CafeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCafeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            binding.rateingTextView.text = "$rating"
        }
    }
}