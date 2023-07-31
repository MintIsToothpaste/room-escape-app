package com.chanwoong.myroomescapeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.chanwoong.myroomescapeapp.databinding.ActivityMainBinding
import com.chanwoong.myroomescapeapp.fragment.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavigation()
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

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_container, fragment, "")
            .commit()
    }

}