package com.chanwoong.myroomescapeapp.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chanwoong.myroomescapeapp.CafeDetailActivity
import com.chanwoong.myroomescapeapp.databinding.ItemLayoutRecommendedThemeBinding
import com.chanwoong.myroomescapeapp.viewmodels.RecommendedThemeViewModel
import com.google.firebase.storage.FirebaseStorage

class RecommendedThemeRecyclerViewAdapter(private val viewModel: RecommendedThemeViewModel) :
    RecyclerView.Adapter<RecommendedThemeRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemLayoutRecommendedThemeBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun setContents(pos: Int) {
            val storage: FirebaseStorage = FirebaseStorage.getInstance("gs://my-room-escape-app.appspot.com/")
            val storageReference = storage.reference

            with (viewModel.getItem(pos)) {
                val pathReference = storageReference.child("recommendedTheme/$url")

                binding.themeNameTextView.text = themeName
                binding.cafeNameTextView.text = cafeName

                pathReference.downloadUrl.addOnSuccessListener { uri ->
                    Glide.with(binding.recommendedThemeImageView)
                        .load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .centerCrop()
                        .into(binding.recommendedThemeImageView)

                    binding.recommendedThemeImageView.clipToOutline = true
                }

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, CafeDetailActivity::class.java)
                    ContextCompat.startActivity(itemView.context, intent, null)
                }
            }

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemLayoutRecommendedThemeBinding.inflate(layoutInflater, viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setContents(position)

    }

    override fun getItemCount() = viewModel.itemsSize


}