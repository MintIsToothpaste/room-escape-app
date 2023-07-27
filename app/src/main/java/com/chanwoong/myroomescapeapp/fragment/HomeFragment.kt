package com.chanwoong.myroomescapeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.chanwoong.myroomescapeapp.adapter.GridRecyclerViewAdapter
import com.chanwoong.myroomescapeapp.adapter.ViewPagerAdapter
import com.chanwoong.myroomescapeapp.databinding.FragmentHomeBinding
import com.chanwoong.myroomescapeapp.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestPermLauncher: ActivityResultLauncher<Array<String>>
    var retryCount = 0

    private lateinit var gridRecyclerViewAdapter: GridRecyclerViewAdapter
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        requestPermLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
                val retry = map.filter { !it.value }.keys.toTypedArray()
                if (retry.isNotEmpty() && retryCount < 2) {
                    requestPermLauncher.launch(retry)
                    retryCount
                }
            }
        retryCount = 0
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGridRecyclerView()
        initViewPager2()
        subscribeObservers()
        autoScrollViewPager()
    }

    private fun initGridRecyclerView(){
        gridRecyclerView.apply {
            gridRecyclerViewAdapter = GridRecyclerViewAdapter()
            layoutManager = GridLayoutManager(this@HomeFragment.context, 5)
            adapter = gridRecyclerViewAdapter

            binding.gridRecyclerView.setHasFixedSize(true)
        }
    }

    private fun initViewPager2() {
        viewPager2.apply {
            viewPagerAdapter = ViewPagerAdapter()
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tv_page_number.text = "${position + 1}"
                    //직접 유저가 스크롤했을 떄!
                    viewModel.setCurrentPosition(position)
                }
            })
        }

    }

    private fun subscribeObservers() {
        viewModel.gridItemList.observe(viewLifecycleOwner, Observer {gridItemList->
            gridRecyclerViewAdapter.submitList(gridItemList)
        })

        viewModel.bannerItemList.observe(viewLifecycleOwner, Observer { bannerItemList ->
            viewPagerAdapter.submitList(bannerItemList)
        })

        viewModel.currentPosition.observe(viewLifecycleOwner, Observer { currentPosition ->
            viewPager2.currentItem = currentPosition
        })
    }

    private fun autoScrollViewPager() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            while (viewLifecycleOwner.lifecycleScope.isActive) {
                delay(5000)
                viewModel.getCurrentPosition()?.let {
                    viewModel.setCurrentPosition(it.plus(1) % 3)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getBannerItems()
        viewModel.getGridItems()
    }
}