package com.chanwoong.myroomescapeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.chanwoong.myroomescapeapp.adapter.GridRecyclerViewAdapter
import com.chanwoong.myroomescapeapp.adapter.ViewPagerAdapter
import com.chanwoong.myroomescapeapp.databinding.ActivityMainBinding
import com.chanwoong.myroomescapeapp.model.data.buttonBannerItemList
import com.chanwoong.myroomescapeapp.model.data.buttonGridItemList
import com.chanwoong.myroomescapeapp.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var gridRecyclerViewAdapter: GridRecyclerViewAdapter
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val viewModel by viewModels<MainActivityViewModel>()
    private var isRunning = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.setGridItems(buttonGridItemList)
        viewModel.setBannerItems(buttonBannerItemList)

        gridRecyclerView.apply {
            gridRecyclerViewAdapter = GridRecyclerViewAdapter()
            layoutManager = GridLayoutManager(this@MainActivity, 5)
            adapter = gridRecyclerViewAdapter

            binding.gridRecyclerView.setHasFixedSize(true)
        }

        initViewPager2()
        subscribeObservers()
    }

    private fun initViewPager2() {
        viewPager2.apply {
            viewPagerAdapter = ViewPagerAdapter()
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    isRunning=true
                    tv_page_number.text = "${position + 1}"

                    //직접 유저가 스크롤했을 떄!
                    viewModel.setCurrentPosition(position)
                }
            })
        }

    }

    private fun subscribeObservers() {
        viewModel.gridItemList.observe(this, Observer {gridItemList->
            gridRecyclerViewAdapter.submitList(gridItemList)
        })

        viewModel.bannerItemList.observe(this, Observer { bannerItemList ->
            viewPagerAdapter.submitList(bannerItemList)
        })

        viewModel.currentPosition.observe(this, Observer { currentPosition ->
            viewPager2.currentItem = currentPosition
        })
    }

    private fun autoScrollViewPager() {
        lifecycleScope.launch {
            whenResumed {
                while (isRunning) {
                    delay(3000)
                    viewModel.getCurrentPosition()?.let {
                        viewModel.setCurrentPosition((it.plus(1)) % 5)
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        isRunning = false
    }

    override fun onResume() {
        super.onResume()
        isRunning = true
    }

}