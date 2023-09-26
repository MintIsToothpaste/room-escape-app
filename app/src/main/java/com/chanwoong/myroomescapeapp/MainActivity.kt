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

}