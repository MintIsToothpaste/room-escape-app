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
import com.chanwoong.myroomescapeapp.adapter.CafeDetailRecyclerViewAdapter
import com.chanwoong.myroomescapeapp.databinding.ActivityCafeDetailBinding
import com.chanwoong.myroomescapeapp.viewmodels.CafeViewModel
import com.chanwoong.myroomescapeapp.viewmodels.ThemeViewModel
import com.google.firebase.storage.FirebaseStorage

class CafeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCafeDetailBinding

    private val viewModelTheme by viewModels<ThemeViewModel>()
    private val viewModelCafe by viewModels<CafeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectCafe = intent.getStringExtra("select")
        viewModelTheme.setCafe(selectCafe)

        binding.cafeNameEditText.text = selectCafe
        binding.cafeNameEditText2.text = selectCafe

        val storage: FirebaseStorage = FirebaseStorage.getInstance("gs://my-room-escape-app.appspot.com/")
        val storageReference = storage.reference

        var url = ""
        for(i: Int in 0 until viewModelCafe.itemsSize){
            if(viewModelCafe.getItem(i).name == selectCafe) url = viewModelCafe.getItem(i).url
        }
        val pathReference = storageReference.child("cafe/$url")

        pathReference.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(binding.cafeDetailImageView)
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(binding.cafeDetailImageView)

            binding.cafeDetailImageView.clipToOutline = true
        }

        for(i: Int in 0 until viewModelTheme.itemsSize){
            if(viewModelTheme.getItem(i).cafeName == selectCafe) viewModelTheme.addSelectItem(viewModelTheme.getItem(i))
        }

        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            binding.rateingTextView.text = "$rating"
        }

        initCafeDetailRecyclerView()
    }

    private fun initCafeDetailRecyclerView(){
        binding.cafeDetailRecyclerView.adapter = CafeDetailRecyclerViewAdapter(viewModelTheme)
        binding.cafeDetailRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cafeDetailRecyclerView.setHasFixedSize(true)

        // 구분선 넣기
        val dividerItemDecoration =
            DividerItemDecoration(this, LinearLayoutManager(this).orientation)

        binding.cafeDetailRecyclerView.addItemDecoration(dividerItemDecoration)

        Log.d(ContentValues.TAG, "리사이클러뷰 순서 확인")
    }
}