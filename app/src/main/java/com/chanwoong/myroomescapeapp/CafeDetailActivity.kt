package com.chanwoong.myroomescapeapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chanwoong.myroomescapeapp.adapter.CafeDetailRecyclerViewAdapter
import com.chanwoong.myroomescapeapp.adapter.CafeReviewRecyclerViewAdapter
import com.chanwoong.myroomescapeapp.databinding.ActivityCafeDetailBinding
import com.chanwoong.myroomescapeapp.viewmodels.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_cafe_detail.*

class CafeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCafeDetailBinding
    private val db = Firebase.firestore
    private var selectCafe = ""

    private val viewModelTheme by viewModels<ThemeViewModel>()
    private val viewModelCafe by viewModels<CafeViewModel>()
    private val viewModelCafeReview by viewModels<CafeReviewModel>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectCafe = intent.getStringExtra("select").toString()

        viewModelTheme.clearItem()
        viewModelCafe.clearItem()

        binding.cafeNameEditText.text = selectCafe
        binding.cafeNameEditText2.text = selectCafe

        val storage: FirebaseStorage = FirebaseStorage.getInstance("gs://my-room-escape-app.appspot.com/")
        val storageReference = storage.reference

        var url = ""
        var cafeRating = 0.0F
        for(i: Int in 0 until viewModelCafe.itemsSize){
            if(viewModelCafe.getItem(i).name == selectCafe) {
                url = viewModelCafe.getItem(i).url
                cafeRating = viewModelCafe.getItem(i).rating
            }
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

        binding.ratingBar.rating = cafeRating
        binding.rateingTextView.text = "${cafeRating.toDouble()}"

        binding.ratingBar2.rating = cafeRating
        binding.rateingTextView2.text = "${cafeRating.toDouble()}"

        initCafeDetailRecyclerView()
        initCafeReviewRecyclerView()
        attachSnapshotListener()

        binding.plusButton.setOnClickListener {

        }

        binding.reviewButton.setOnClickListener {

        }

        // LiveData의 value의 변경을 감지하고 호출
        viewModelCafeReview.itemsListData.observe(this, Observer {
            binding.cafeReviewRecyclerView.adapter?.notifyDataSetChanged()
        })
    }

    private fun initCafeDetailRecyclerView(){
        binding.cafeDetailRecyclerView.adapter = CafeDetailRecyclerViewAdapter(viewModelTheme)
        binding.cafeDetailRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cafeDetailRecyclerView.setHasFixedSize(true)

        // 구분선 넣기
        val dividerItemDecoration =
            DividerItemDecoration(this, LinearLayoutManager(this).orientation)

        binding.cafeDetailRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun initCafeReviewRecyclerView(){
        binding.cafeReviewRecyclerView.adapter = CafeReviewRecyclerViewAdapter(viewModelCafeReview)
        binding.cafeReviewRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cafeReviewRecyclerView.setHasFixedSize(true)

        // 구분선 넣기
        val dividerItemDecoration =
            DividerItemDecoration(this, LinearLayoutManager(this).orientation)

        binding.cafeReviewRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun attachSnapshotListener(){
        val docRef = db.collection("cafeReview")

        docRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document != null) {
                        val nickname = document["nickname"].toString()
                        val time = document["time"].toString()
                        val rating = document["rating"].toString()
                        val review = document["review"].toString()
                        val cafe = document["cafe"].toString()

                        val item = CafeReviewList(
                            nickname,
                            time,
                            rating,
                            review,
                            cafe
                        )

                        if(selectCafe.equals(cafe)) viewModelCafeReview.addItem(item)

                    } else {
                        Log.d(ContentValues.TAG, "DocumentSnapshot null")
                    }
                }
            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "DocumentSnapshot fail")
            }
    }

}