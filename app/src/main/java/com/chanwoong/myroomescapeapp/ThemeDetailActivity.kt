package com.chanwoong.myroomescapeapp

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chanwoong.myroomescapeapp.adapter.ThemeDetailRecyclerViewAdapter
import com.chanwoong.myroomescapeapp.databinding.ActivityThemeDetailBinding
import com.chanwoong.myroomescapeapp.viewmodels.CafeViewModel
import com.chanwoong.myroomescapeapp.viewmodels.ThemeViewModel
import com.google.firebase.storage.FirebaseStorage

class ThemeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThemeDetailBinding

    private val viewModelTheme by viewModels<ThemeViewModel>()
    private val viewModelCafe by viewModels<CafeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectCafe = intent.getStringExtra("selectCafe")
        val selectTheme = intent.getStringExtra("selectTheme")

        binding.themeNameEditText.text = selectTheme
        binding.themeNameEditText2.text = selectTheme

        val storage: FirebaseStorage = FirebaseStorage.getInstance("gs://my-room-escape-app.appspot.com/")
        val storageReference = storage.reference

        var url = ""
        for(i: Int in 0 until viewModelTheme.itemsSize){
            if(viewModelTheme.getItem(i).name == selectTheme) url = viewModelTheme.getItem(i).url
        }
        val pathReference = storageReference.child("recommendedTheme/$url")

        pathReference.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(binding.themeDetailImageView)
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(binding.themeDetailImageView)

            binding.themeDetailImageView.clipToOutline = true
        }

        for(i: Int in 0 until viewModelCafe.itemsSize){
            if(viewModelCafe.getItem(i).name == selectCafe) viewModelCafe.addSelectItem(viewModelCafe.getItem(i))
        }

        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            binding.rateingTextView.text = "$rating"
        }

        initThemeDetailRecyclerView()
    }

    private fun initThemeDetailRecyclerView(){
        binding.themeDetailRecyclerView.adapter = ThemeDetailRecyclerViewAdapter(viewModelCafe)
        binding.themeDetailRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.themeDetailRecyclerView.setHasFixedSize(true)

        // 구분선 넣기
        val dividerItemDecoration =
            DividerItemDecoration(this, LinearLayoutManager(this).orientation)

        binding.themeDetailRecyclerView.addItemDecoration(dividerItemDecoration)

        Log.d(ContentValues.TAG, "리사이클러뷰 순서 확인")
    }
}