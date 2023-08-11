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
import com.chanwoong.myroomescapeapp.adapter.CafeRecyclerViewAdapter
import com.chanwoong.myroomescapeapp.adapter.ProfileMenuRecyclerViewAdapter
import com.chanwoong.myroomescapeapp.databinding.FragmentProfileBinding
import com.chanwoong.myroomescapeapp.viewmodels.CafeViewModel
import com.chanwoong.myroomescapeapp.viewmodels.ProfileMenuViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_cafe.*

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestPermLauncher: ActivityResultLauncher<Array<String>>
    var retryCount = 0

    private val viewModel by viewModels<ProfileMenuViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
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
        val binding = FragmentProfileBinding.bind(view)

        initCafeRecyclerView()
    }

    private fun initCafeRecyclerView(){
        binding.profileRecyclerView.adapter = ProfileMenuRecyclerViewAdapter(viewModel)
        binding.profileRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.profileRecyclerView.setHasFixedSize(true)

    }
}