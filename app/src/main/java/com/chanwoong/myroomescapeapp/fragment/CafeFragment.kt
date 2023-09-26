package com.chanwoong.myroomescapeapp.fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanwoong.myroomescapeapp.R
import com.chanwoong.myroomescapeapp.SlideUpDialog
import com.chanwoong.myroomescapeapp.adapter.CafeRecyclerViewAdapter
import com.chanwoong.myroomescapeapp.databinding.FragmentCafeBinding
import com.chanwoong.myroomescapeapp.model.KakaoAPI
import com.chanwoong.myroomescapeapp.model.ResultSearchKeyword
import com.chanwoong.myroomescapeapp.model.RoomsResponse
import com.chanwoong.myroomescapeapp.model.RoomsService
import com.chanwoong.myroomescapeapp.viewmodels.CafeList
import com.chanwoong.myroomescapeapp.viewmodels.CafeViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_cafe.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CafeFragment : Fragment(){
    private var _binding: FragmentCafeBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestPermLauncher: ActivityResultLauncher<Array<String>>
    var retryCount = 0

    private val listItems = arrayListOf<CafeList>() // 리사이클러 뷰 아이템
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

        initToolbar()
        initTabLayout()
        //kakaoLocalApi()
        //naverLocalApi()
        initCafeRecyclerView()


    }

    private fun kakaoLocalApi(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(KakaoAPI::class.java) // 통신 인터페이스를 객체로 생성
        val call = api.getSearchKeyword("KakaoAK " + "a09345f5389e7045fee82d70fd034e3d", "방탈출", 15) // 검색 조건 입력

        // API 서버에 요청
        call.enqueue(object: Callback<ResultSearchKeyword> {
            override fun onResponse(call: Call<ResultSearchKeyword>, response: Response<ResultSearchKeyword>) {
                // 통신 성공
                addItemsAndMarkers(response.body())
                Log.w("LocalSearch", "통신 성공 " + response.body())
                Log.d("ERROR RESPONSE",response.errorBody()?.string().toString())
            }

            override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
                // 통신 실패
                Log.w("LocalSearch", "통신 실패: ${t.message}")
            }
        })
    }

    // 검색 결과 처리 함수
    private fun addItemsAndMarkers(searchResult: ResultSearchKeyword?) {
        if (!searchResult?.documents.isNullOrEmpty()) {
            // 검색 결과 있음
            for (document in searchResult!!.documents) {
            // 결과를 리사이클러 뷰에 추가
                val item = CafeList(document.place_name,
                    document.x.toDouble(),
                    document.y.toDouble(), "", "")
                viewModel.addItem(item)
            }
            initCafeRecyclerView()
        } else {
            // 검색 결과 없음
            Toast.makeText(context, "검색 결과가 없습니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun naverLocalApi(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(RoomsService::class.java).also {
            it.getRoomsList("hmEcUzVuPllHwwX9I0F8", "pb2FgnUPG9", query = "갈비집")
                .enqueue(object  : Callback<RoomsResponse> {
                    override fun onResponse(
                        call: Call<RoomsResponse>,
                        response: Response<RoomsResponse>
                    ) {
                        if (response.isSuccessful.not()) {
                            // 통신 실패
                            Toast.makeText(
                                context,
                                "Error!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            return
                        }
                        // 통신 성공
                        response.body()?.let { dto ->
                            Log.d(TAG, "$it 로그 입니다")

                            Log.d("ERROR RESPONSE",response.errorBody()?.string().toString())
                        }
                }

                    override fun onFailure(call: Call<RoomsResponse>, t: Throwable) {
                        // 통신 실패 시
                    }
                })
        }
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
                    onSlideUpDialog()
                    true
                }
                else -> false
            }
        }
    }

    private fun onSlideUpDialog() {
        val contentView: View = (activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.popup_slideup, null)
        val slideupPopup = SlideUpDialog.Builder(cafeRecyclerView.context)
            .setContentView(contentView)
            .create()
        slideupPopup.show()
        contentView.findViewById<Button>(R.id.save).setOnClickListener {
            slideupPopup.dismissAnim()
        }
        contentView.findViewById<Button>(R.id.recommend).setOnClickListener {

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