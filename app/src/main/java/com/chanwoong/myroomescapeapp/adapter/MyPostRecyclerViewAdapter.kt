package com.chanwoong.myroomescapeapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chanwoong.myroomescapeapp.databinding.ItemLayoutMyPostBinding
import com.chanwoong.myroomescapeapp.viewmodels.MyPostViewModel

class MyPostRecyclerViewAdapter (private val viewModel: MyPostViewModel) :
    RecyclerView.Adapter<MyPostRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemLayoutMyPostBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun setContents(pos: Int) {

            with (viewModel.getItem(pos)) {
                binding.myPostName.text = title
                binding.myPostContent.text = post
                binding.myPostNickname.text = nickname
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemLayoutMyPostBinding.inflate(layoutInflater, viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setContents(position)

    }

    override fun getItemCount() = viewModel.itemsSize


}