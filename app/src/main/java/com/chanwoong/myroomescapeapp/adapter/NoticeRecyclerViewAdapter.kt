package com.chanwoong.myroomescapeapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chanwoong.myroomescapeapp.databinding.ItemLayoutCommunityBinding
import com.chanwoong.myroomescapeapp.databinding.ItemLayoutNoticeBinding
import com.chanwoong.myroomescapeapp.viewmodels.CommunityViewModel
import com.chanwoong.myroomescapeapp.viewmodels.NoticeViewModel

class NoticeRecyclerViewAdapter (private val viewModel: NoticeViewModel) :
    RecyclerView.Adapter<NoticeRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemLayoutNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun setContents(pos: Int) {

            with (viewModel.getItem(pos)) {
                binding.noticeName.text = title
                binding.noticeContent.text = post
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemLayoutNoticeBinding.inflate(layoutInflater, viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setContents(position)

    }

    override fun getItemCount() = viewModel.itemsSize


}