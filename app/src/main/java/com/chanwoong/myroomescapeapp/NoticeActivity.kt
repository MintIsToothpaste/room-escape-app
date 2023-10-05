package com.chanwoong.myroomescapeapp

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanwoong.myroomescapeapp.adapter.NoticeRecyclerViewAdapter
import com.chanwoong.myroomescapeapp.databinding.ActivityNoticeBinding
import com.chanwoong.myroomescapeapp.viewmodels.NoticeViewModel

class NoticeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoticeBinding
    private val viewModel by viewModels<NoticeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCommunityRecyclerView()
    }

    private fun initCommunityRecyclerView(){
        binding.noticeReclerView.adapter = NoticeRecyclerViewAdapter(viewModel)
        binding.noticeReclerView.layoutManager = LinearLayoutManager(this)
        binding.noticeReclerView.setHasFixedSize(true)

        // 구분선 넣기
        val dividerItemDecoration =
            DividerItemDecoration(this, LinearLayoutManager(this).orientation)

        binding.noticeReclerView.addItemDecoration(dividerItemDecoration)

        Log.d(ContentValues.TAG, "리사이클러뷰 순서 확인")
    }
}