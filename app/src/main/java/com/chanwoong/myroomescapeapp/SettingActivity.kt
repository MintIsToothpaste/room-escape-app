package com.chanwoong.myroomescapeapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chanwoong.myroomescapeapp.databinding.ActivitySettingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SettingActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nickNameButton.setOnClickListener {
            val newNickname = binding.nickNameChangeEditText.text.toString().trim()
            val user = Firebase.auth.currentUser

            user?.let {
                for (profile in it.providerData) {
                    // UID specific to the provider
                    val uid = profile.uid

                    val washingtonRef = db.collection("users").document(uid)

                    washingtonRef
                        .update("nickname", newNickname)
                        .addOnSuccessListener { Toast.makeText(this, "닉네임이 변경되었습니다", Toast.LENGTH_SHORT).show() }
                        .addOnFailureListener { Toast.makeText(this, "닉네임 변경 오류", Toast.LENGTH_SHORT).show() }
                }
            }
        }

        binding.passwordButton.setOnClickListener {
            val user = Firebase.auth.currentUser
            val newPassword = binding.passwordChangeEditText.text.toString().trim()

            user!!.updatePassword(newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user.let {
                            for (profile in it.providerData) {
                                // UID specific to the provider
                                val uid = profile.uid

                                val washingtonRef = db.collection("users").document(uid)

                                washingtonRef
                                    .update("password", newPassword)
                                    .addOnSuccessListener { Toast.makeText(this, "패스워드가 변경되었습니다", Toast.LENGTH_SHORT).show() }
                                    .addOnFailureListener { Toast.makeText(this, "패스워드 변경 오류", Toast.LENGTH_SHORT).show() }
                            }
                        }
                    }
                }
        }

        binding.logout.setOnClickListener {
            Firebase.auth.signOut()
            finish()
        }

        binding.withdrawal.setOnClickListener{

        }

    }


}