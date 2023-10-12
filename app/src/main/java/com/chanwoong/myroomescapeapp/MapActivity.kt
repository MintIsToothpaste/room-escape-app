package com.chanwoong.myroomescapeapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import com.chanwoong.myroomescapeapp.databinding.ActivityMapBinding
import com.chanwoong.myroomescapeapp.model.KakaoAPI
import com.chanwoong.myroomescapeapp.model.ResultSearchKeyword
import com.chanwoong.myroomescapeapp.viewmodels.CafeList
import com.chanwoong.myroomescapeapp.viewmodels.CafeViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import kotlinx.android.synthetic.main.activity_map.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapActivity: AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMapBinding
    private val viewModel by viewModels<CafeViewModel>()
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapView = binding.mapView.map_view
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this);

        with (viewModel.getItem(0)) {

        }


    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {

        viewModel.items.forEach { room ->
            val marker = Marker()
            marker.position = LatLng(room.x, room.y)
            marker.map = naverMap
        }

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
                    document.y.toDouble(),"","")
                viewModel.addItem(item)
            }
        } else {
            // 검색 결과 없음
            Toast.makeText(this, "검색 결과가 없습니다", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}