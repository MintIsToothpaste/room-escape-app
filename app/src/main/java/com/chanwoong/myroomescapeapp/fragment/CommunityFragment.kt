package com.chanwoong.myroomescapeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanwoong.myroomescapeapp.R
import com.chanwoong.myroomescapeapp.adapter.CommunityRecyclerViewAdapter
import com.chanwoong.myroomescapeapp.databinding.FragmentCommunityBinding
import com.chanwoong.myroomescapeapp.viewmodels.CommunityViewModel
import kotlinx.android.synthetic.main.fragment_community.*


class CommunityFragment : Fragment() {
    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestPermLauncher: ActivityResultLauncher<Array<String>>
    var retryCount = 0

    private val viewModel by viewModels<CommunityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
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
        val binding = FragmentCommunityBinding.bind(view)

        initToolbar()
        initCommunityRecyclerView()
    }

    private fun initToolbar(){
        binding.toolbarCommunity.inflateMenu(R.menu.toolbar_community_menu)
        binding.toolbarCommunity.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.app_bar_search -> {
                    true
                }
                else -> false
            }
        }
    }

    private fun initCommunityRecyclerView(){
        binding.communityRecyclerView.adapter = CommunityRecyclerViewAdapter(viewModel)
        binding.communityRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.communityRecyclerView.setHasFixedSize(true)

        // 구분선 넣기
        val dividerItemDecoration =
            DividerItemDecoration(communityRecyclerView.context, LinearLayoutManager(context).orientation)

        binding.communityRecyclerView.addItemDecoration(dividerItemDecoration)
    }
}