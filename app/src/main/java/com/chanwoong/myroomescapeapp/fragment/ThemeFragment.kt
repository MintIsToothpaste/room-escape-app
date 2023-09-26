package com.chanwoong.myroomescapeapp.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanwoong.myroomescapeapp.R
import com.chanwoong.myroomescapeapp.SlideUpDialog
import com.chanwoong.myroomescapeapp.adapter.ThemeRecyclerViewAdapter
import com.chanwoong.myroomescapeapp.databinding.FragmentThemeBinding
import com.chanwoong.myroomescapeapp.viewmodels.ThemeViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_cafe.*
import kotlinx.android.synthetic.main.fragment_theme.*

class ThemeFragment : Fragment() {
    private var _binding: FragmentThemeBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestPermLauncher: ActivityResultLauncher<Array<String>>
    var retryCount = 0

    private val viewModel by viewModels<ThemeViewModel>()

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
        initThemeRecyclerView()

    }

    private fun initThemeRecyclerView(){
        binding.themeRecyclerView.adapter = ThemeRecyclerViewAdapter(viewModel)
        binding.themeRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.themeRecyclerView.setHasFixedSize(true)

        // 구분선 넣기
        val dividerItemDecoration =
            DividerItemDecoration(themeRecyclerView.context, LinearLayoutManager(context).orientation)

        binding.themeRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun initToolbar(){
        binding.toolbarTheme.inflateMenu(R.menu.toolbar_theme_menu)
        binding.toolbarTheme.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.app_bar_search -> {
                    true
                }
                R.id.app_bar_filter -> {
                    onSlideUpDialog()
                    true
                }
                else -> false
            }
        }
    }

    private fun onSlideUpDialog() {
        val contentView: View = (activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.popup_slideup2, null)
        val slideupPopup = SlideUpDialog.Builder(themeRecyclerView.context)
            .setContentView(contentView)
            .create()
        slideupPopup.show()
        contentView.findViewById<Button>(R.id.save).setOnClickListener {
            slideupPopup.dismissAnim()
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