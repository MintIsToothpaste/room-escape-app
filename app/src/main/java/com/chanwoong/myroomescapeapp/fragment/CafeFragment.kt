package com.chanwoong.myroomescapeapp.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanwoong.myroomescapeapp.R
import com.chanwoong.myroomescapeapp.adapter.CafeRecyclerViewAdapter
import com.chanwoong.myroomescapeapp.databinding.FragmentCafeBinding
import com.chanwoong.myroomescapeapp.viewmodels.CafeViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_cafe.*

class CafeFragment : Fragment(){
    private var _binding: FragmentCafeBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestPermLauncher: ActivityResultLauncher<Array<String>>
    var retryCount = 0

    private lateinit var cafeRecyclerViewAdapter: CafeRecyclerViewAdapter
    private val viewModel by viewModels<CafeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCafeBinding.inflate(inflater, container, false)
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

        initCafeRecyclerView()
        initToolbar()
        initTabLayout()

    }

    private fun initCafeRecyclerView(){
        binding.cafeRecyclerView.adapter = CafeRecyclerViewAdapter(viewModel)
        binding.cafeRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.cafeRecyclerView.setHasFixedSize(true)

        // 구분선 넣기
        val dividerItemDecoration =
            DividerItemDecoration(cafeRecyclerView.context, LinearLayoutManager(context).orientation)

        binding.cafeRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun initToolbar(){
        binding.toolbarCafe.inflateMenu(R.menu.toolbar_cafe_menu)
        binding.toolbarCafe.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.app_bar_search -> {
                    true
                }
                R.id.app_bar_filter -> {
                    true
                }
                else -> false
            }
        }
    }

    private fun initTabLayout(){
        binding.tabCafe.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.e("TAG", "${tab!!.position}")
                when(tab.position){
                    0 ->{

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab!!.view.setBackgroundColor(Color.TRANSPARENT)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

}