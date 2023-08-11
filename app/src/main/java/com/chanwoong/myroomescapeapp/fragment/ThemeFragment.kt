package com.chanwoong.myroomescapeapp.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chanwoong.myroomescapeapp.R
import com.chanwoong.myroomescapeapp.databinding.FragmentThemeBinding
import com.google.android.material.tabs.TabLayout

class ThemeFragment : Fragment() {
    private var _binding: FragmentThemeBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestPermLauncher: ActivityResultLauncher<Array<String>>
    var retryCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThemeBinding.inflate(inflater, container, false)
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
        val binding = FragmentThemeBinding.bind(view)

        initToolbar()
        initTabLayout()

    }

    private fun initToolbar(){
        binding.toolbarTheme.inflateMenu(R.menu.toolbar_theme_menu)
        binding.toolbarTheme.setOnMenuItemClickListener {
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
        binding.tabTheme.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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