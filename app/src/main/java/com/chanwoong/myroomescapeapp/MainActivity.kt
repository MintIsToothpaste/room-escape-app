package com.chanwoong.myroomescapeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.chanwoong.myroomescapeapp.databinding.ActivityMainBinding
import com.chanwoong.myroomescapeapp.fragment.*
import com.chanwoong.myroomescapeapp.model.KakaoAPI
import com.chanwoong.myroomescapeapp.model.ResultSearchKeyword
import com.chanwoong.myroomescapeapp.viewmodels.CafeViewModel
import com.chanwoong.myroomescapeapp.viewmodels.ListLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<CafeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavigation()
        //kakaoLocalApi()

        //Firebase 초기화
        auth = Firebase.auth

    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser == null)
            startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun setBottomNavigation(){
        binding.bottomNavigation.selectedItemId = R.id.nav_home
        replaceFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener{
            val fragment = when (it.itemId) {
                R.id.nav_cafe -> CafeFragment()
                R.id.nav_theme -> ThemeFragment()
                R.id.nav_home -> HomeFragment()
                R.id.nav_community -> CommunityFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> throw IllegalArgumentException("not found menu item id")
            }
            replaceFragment(fragment)
            true
        }

    }

    fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_container, fragment, "")
            .commit()
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
                val item = ListLayout(document.place_name,
                    document.road_address_name,
                    document.address_name,
                    document.x.toDouble(),
                    document.y.toDouble())
                viewModel.addItem(item)
            }
        } else {
            // 검색 결과 없음
            Toast.makeText(this, "검색 결과가 없습니다", Toast.LENGTH_SHORT).show()
        }
    }

}