package com.chanwoong.myroomescapeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.chanwoong.myroomescapeapp.R
import com.chanwoong.myroomescapeapp.databinding.FragmentCafeBinding

class CafeFragment : Fragment(){
    private var _binding: FragmentCafeBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestPermLauncher: ActivityResultLauncher<Array<String>>
    var retryCount = 0


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
        val binding = FragmentCafeBinding.bind(view)

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
}