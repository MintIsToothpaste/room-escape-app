package com.chanwoong.myroomescapeapp.adapter

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chanwoong.myroomescapeapp.CafeDetailActivity
import com.chanwoong.myroomescapeapp.databinding.ItemLayoutCafeBinding
import com.chanwoong.myroomescapeapp.viewmodels.CafeViewModel
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_layout_cafe.view.*

class CafeRecyclerViewAdapter(private val viewModel: CafeViewModel) :
    RecyclerView.Adapter<CafeRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemLayoutCafeBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun setContents(pos: Int) {
            val storage: FirebaseStorage = FirebaseStorage.getInstance("gs://my-room-escape-app.appspot.com/")
            val storageReference = storage.reference

            with (viewModel.getItem(pos)) {
                val pathReference = storageReference.child("cafe/$url")

                binding.cafeName.text = name

                pathReference.downloadUrl.addOnSuccessListener { uri ->
                    Glide.with(binding.cafeImageView)
                        .load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .centerCrop()
                        .into(binding.cafeImageView)

                    binding.cafeImageView.clipToOutline = true
                }

                itemView.setOnClickListener {
                    viewModel.setCafe(itemView.cafeName.text.toString())

                    val intent = Intent(itemView.context, CafeDetailActivity::class.java)
                    intent.putExtra("select", itemView.cafeName.text.toString())
                    ContextCompat.startActivity(itemView.context, intent, null)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemLayoutCafeBinding.inflate(layoutInflater, viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setContents(position)

    }

    override fun getItemCount() = viewModel.itemsSize


}