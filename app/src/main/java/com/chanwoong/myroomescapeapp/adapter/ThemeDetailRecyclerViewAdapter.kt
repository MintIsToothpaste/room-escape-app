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
import com.chanwoong.myroomescapeapp.databinding.ItemLayoutThemeDetailBinding
import com.chanwoong.myroomescapeapp.viewmodels.CafeViewModel
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_layout_cafe_detail.view.*

class ThemeDetailRecyclerViewAdapter (private val viewModel: CafeViewModel) :
    RecyclerView.Adapter<ThemeDetailRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(private var binding: ItemLayoutThemeDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun setContents(pos: Int) {
            val storage: FirebaseStorage = FirebaseStorage.getInstance("gs://my-room-escape-app.appspot.com/")
            val storageReference = storage.reference

            with (viewModel.getSelectItem(pos)) {
                val pathReference = storageReference.child("cafe/$url")

                binding.cafeNameTextView.text = name

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
                    intent.putExtra("select", itemView.cafeNameTextView.text.toString())
                    ContextCompat.startActivity(itemView.context, intent, null)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemLayoutThemeDetailBinding.inflate(layoutInflater, viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setContents(position)
    }

    override fun getItemCount() = viewModel.selectItemsSize


}