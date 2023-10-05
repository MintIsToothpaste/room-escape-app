package com.chanwoong.myroomescapeapp

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chanwoong.myroomescapeapp.databinding.ActivitySettingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_cafe.view.*


class SettingActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    private lateinit var binding: ActivitySettingBinding
    private var uid = ""
    private val user = Firebase.auth.currentUser

    override fun onStart() {
        super.onStart()

        user?.let {
            uid = it.uid
        }
        setUser(uid)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nickNameButton.setOnClickListener {
            val newNickname = binding.nickNameChangeEditText.text.toString().trim()
            val washingtonRef = db.collection("users").document(uid)

            washingtonRef
                .update("nickname", newNickname)
                .addOnSuccessListener {
                    setUser(uid)
                    Toast.makeText(this, "닉네임이 변경되었습니다", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { Toast.makeText(this, "닉네임이 변경오류", Toast.LENGTH_SHORT).show() }
        }

        binding.passwordButton.setOnClickListener {
            val newPassword = binding.passwordChangeEditText.text.toString().trim()

            user!!.updatePassword(newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val washingtonRef = db.collection("users").document(uid)

                        washingtonRef
                            .update("password", newPassword)
                            .addOnSuccessListener { Toast.makeText(this, "패스워드가 변경되었습니다", Toast.LENGTH_SHORT).show() }
                            .addOnFailureListener { Toast.makeText(this, "패스워드가 변경오류", Toast.LENGTH_SHORT).show() }
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

    @SuppressLint("SetTextI18n")
    private fun setUser(uid : String){
        val docRef = db.collection("users").document(uid)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    binding.textView20.text = document["nickname"].toString()
                    binding.textView18.text = "리뷰 " + document["review"].toString()
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

}