package com.chanwoong.myroomescapeapp.fragment

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.chanwoong.myroomescapeapp.MainActivity
import com.chanwoong.myroomescapeapp.MapActivity
import com.chanwoong.myroomescapeapp.SettingActivity
import com.chanwoong.myroomescapeapp.adapter.GridRecyclerViewAdapter
import com.chanwoong.myroomescapeapp.adapter.RecommendedThemeRecyclerViewAdapter
import com.chanwoong.myroomescapeapp.adapter.ViewPagerAdapter
import com.chanwoong.myroomescapeapp.databinding.FragmentHomeBinding
import com.chanwoong.myroomescapeapp.model.data.buttonGridItemList
import com.chanwoong.myroomescapeapp.viewmodels.HomeViewModel
import com.chanwoong.myroomescapeapp.viewmodels.RecommendedThemeViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_layout_grid.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestPermLauncher: ActivityResultLauncher<Array<String>>
    var activity: MainActivity? = null
    var retryCount = 0

    private val db = Firebase.firestore

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = getActivity() as MainActivity
    }

    override fun onDetach() {
        super.onDetach()

        activity = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGridRecyclerView()
        initRecommendedThemeRecyclerView()
        initViewPager2()
        subscribeObservers()
        autoScrollViewPager()
        gridRecyclerViewClickListener()

    }

    private fun gridRecyclerViewClickListener(){
        gridRecyclerViewAdapter.setItemClickListener(object : GridRecyclerViewAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {
                Log.d("SSS", "${position}번 리스트 선택")

                if(position <= 4){
                    activity?.replaceFragment(CafeFragment())
                }

                else if (position in 5..9){
                    activity?.replaceFragment(ThemeFragment())
                }

                else if (position == 10){
                    activity?.replaceFragment(CafeFragment())
                }

                else if (position == 11){
                    val intent = Intent(context, MapActivity::class.java)
                    context?.let { ContextCompat.startActivity(it, intent, null) }
                }

                else if (position == 12){
                    activity?.replaceFragment(CommunityFragment())
                }
            }
        })
    }

    private fun initRecommendedThemeRecyclerView(){
        binding.recommendedThemeRecyclerView.adapter = RecommendedThemeRecyclerViewAdapter(
            RecommendedThemeViewModel()
        )
        binding.recommendedThemeRecyclerView.layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL, false)
        binding.recommendedThemeRecyclerView.setHasFixedSize(true)
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
                @SuppressLint("SetTextI18n")
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