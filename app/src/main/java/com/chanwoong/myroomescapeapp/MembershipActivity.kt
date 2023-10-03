package com.chanwoong.myroomescapeapp


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chanwoong.myroomescapeapp.databinding.ActivityMembershipBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern


class MembershipActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMembershipBinding
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMembershipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.loginButton.isEnabled = false

        binding.duplicateButton.setOnClickListener {
            val testId = binding.emailMembershipEditText.text.toString().trim()
            val password = binding.passwordMembershipEditText.text.toString().trim()
            val password2 = binding.passwordMembershipEditText2.text.toString().trim()
            val docRef = db.collection("users")

            var checkId = false

            docRef.get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if (document != null) {
                            if(document["email"].toString() == testId){
                                Toast.makeText(this, "중복 이메일이 있습니다", Toast.LENGTH_SHORT).show()
                                Log.d(TAG, "Duplicate email exists")
                                checkId = true
                            }
                        } else {
                            Log.d(TAG, "No such document")
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }

            if(!checkEmail(testId)){
                checkId = true
                Toast.makeText(this, "이메일 형식에 맞게 입력하세요", Toast.LENGTH_SHORT).show()
            }

            if(password != password2){
                checkId = true
                Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            }

            if (!checkId) {
                binding.loginButton.isEnabled = true
                Toast.makeText(this, "사용 가능한 이메일입니다", Toast.LENGTH_SHORT).show()
            }

        }

        binding.loginButton.setOnClickListener {

            val email = binding.emailMembershipEditText.text.toString().trim()
            val password = binding.passwordMembershipEditText.text.toString().trim()
            val nickname = binding.nicknameEditText.text.toString().trim()

            createUser(email, password, nickname)

        }
    }

    private fun checkEmail(testId: String): Boolean {
        // 검사 정규식
        val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        return Pattern.matches(emailValidation, testId)
    }

    private fun createUser(email: String, password: String, nickname: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                    firestoreUserCreate(email, password, nickname)
                    finish()
                } else {
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
            }
    }

    private fun firestoreUserCreate(email: String, password: String, nickname: String){
        val users = Firebase.auth.currentUser

        val user = hashMapOf(
            "email" to email,
            "password" to password,
            "nickname" to nickname
        )

        if (users != null) {
            db.collection("users").document(users.uid)
                .set(user)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully written!")

                    startActivity(
                        Intent(this, LoginActivity::class.java)
                    )
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }

    }



}