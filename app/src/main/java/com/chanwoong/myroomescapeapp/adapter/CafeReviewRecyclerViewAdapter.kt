package com.chanwoong.myroomescapeapp.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chanwoong.myroomescapeapp.ThemeDetailActivity
import com.chanwoong.myroomescapeapp.databinding.ItemLayoutCafeDetailBinding
import com.chanwoong.myroomescapeapp.databinding.ItemLayoutCafeReviewBinding
import com.chanwoong.myroomescapeapp.viewmodels.CafeReviewModel
import com.chanwoong.myroomescapeapp.viewmodels.ThemeViewModel
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_layout_cafe_detail.view.*

class CafeReviewRecyclerViewAdapter (private val viewModel: CafeReviewModel) :
    RecyclerView.Adapter<CafeReviewRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemLayoutCafeReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun setContents(pos: Int) {
            with (viewModel.getItem(pos)) {
                binding.reviewTextView.text = review
                binding.nickNameReview.text = nickname
                binding.timeReview.text = time

                binding.ratingBarReview.rating = rating.toFloat()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemLayoutCafeReviewBinding.inflate(layoutInflater, viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setContents(position)
    }

    override fun getItemCount() = 3

}