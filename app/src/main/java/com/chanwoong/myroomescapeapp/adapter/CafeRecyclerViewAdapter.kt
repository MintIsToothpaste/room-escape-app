package com.chanwoong.myroomescapeapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chanwoong.myroomescapeapp.databinding.ItemLayoutCafeBinding
import com.chanwoong.myroomescapeapp.viewmodels.CafeViewModel

class CafeRecyclerViewAdapter(private val viewModel: CafeViewModel) :
    RecyclerView.Adapter<CafeRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemLayoutCafeBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun setContents(pos: Int) {
            with (viewModel.getItem(pos)) {
                binding.cafeName.text = name

                //Glide.with(binding.cafeImageView).load(image).into(binding.cafeImageView)

                binding.cafeImageView.setOnClickListener {

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