package com.chanwoong.myroomescapeapp.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.chanwoong.myroomescapeapp.MapActivity
import com.chanwoong.myroomescapeapp.databinding.ItemLayoutProfileBinding
import com.chanwoong.myroomescapeapp.viewmodels.ProfileMenuViewModel
import kotlinx.android.synthetic.main.item_layout_grid.view.*

class ProfileMenuRecyclerViewAdapter(private val viewModel: ProfileMenuViewModel) :
    RecyclerView.Adapter<ProfileMenuRecyclerViewAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding: ItemLayoutProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun setContents(pos: Int) {
            with (viewModel.getItem(pos)) {
                binding.profileMenu.text = title


                binding.profileMenu.setOnClickListener {
                    val intent = Intent(itemView.context, MapActivity::class.java)
                    ContextCompat.startActivity(itemView.context, intent, null)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemLayoutProfileBinding.inflate(layoutInflater, viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setContents(position)

    }

    override fun getItemCount() = viewModel.itemsSize
}