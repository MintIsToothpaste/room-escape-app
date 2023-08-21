package com.chanwoong.myroomescapeapp


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chanwoong.myroomescapeapp.databinding.ActivityMembershipBinding


class MembershipActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMembershipBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMembershipBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }


}