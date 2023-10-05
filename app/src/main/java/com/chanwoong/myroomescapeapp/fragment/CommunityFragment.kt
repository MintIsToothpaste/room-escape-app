package com.chanwoong.myroomescapeapp.fragment

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanwoong.myroomescapeapp.CommunityPostingActivity
import com.chanwoong.myroomescapeapp.LoginActivity
import com.chanwoong.myroomescapeapp.R
import com.chanwoong.myroomescapeapp.adapter.CommunityRecyclerViewAdapter
import com.chanwoong.myroomescapeapp.databinding.FragmentCommunityBinding
import com.chanwoong.myroomescapeapp.viewmodels.CafeList
import com.chanwoong.myroomescapeapp.viewmodels.Community
import com.chanwoong.myroomescapeapp.viewmodels.CommunityViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_community.*
import kotlinx.coroutines.delay


class CommunityFragment : Fragment() {
    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestPermLauncher: ActivityResultLauncher<Array<String>>
    var retryCount = 0
    val db = Firebase.firestore

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

    override fun onStart() {
        super.onStart()
        attachSnapshotListener()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCommunityBinding.bind(view)

        initToolbar()
        Handler(Looper.getMainLooper()).postDelayed({
            initCommunityRecyclerView()
        }, 800)

        binding.postingButton.setOnClickListener {
            val intent = Intent(context, CommunityPostingActivity::class.java)
            context?.let { it1 -> ContextCompat.startActivity(it1, intent, null) }
        }
    }

    private fun attachSnapshotListener(){
        val docRef = db.collection("posting")

        docRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document != null) {
                        val post = document["post"].toString()
                        val nickname = document["nickname"].toString()
                        val title = document["title"].toString()

                        val item = Community(
                            title,
                            post,
                            nickname
                        )

                        Log.d(ContentValues.TAG, item.toString())

                        viewModel.addItem(item)
                    } else {
                        Log.d(ContentValues.TAG, "DocumentSnapshot null")
                    }
                }
            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "DocumentSnapshot fail")
            }

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

        binding.communityRecyclerView.addItemDecoration(dividerItemDecoration) }
}