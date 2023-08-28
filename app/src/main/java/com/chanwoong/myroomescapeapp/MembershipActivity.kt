package com.chanwoong.myroomescapeapp


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chanwoong.myroomescapeapp.databinding.ActivityMembershipBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MembershipActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMembershipBinding
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMembershipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.loginButton.isEnabled = true

        binding.duplicateButton.setOnClickListener {

            db.collection("users")
                .whereEqualTo("email", true)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }

        }

        binding.loginButton.setOnClickListener {

            val email = binding.emailMembershipEditText.text.toString().trim()
            val password = binding.passwordMembershipEditText.text.toString().trim()
            val nickname = binding.nicknameEditText.text.toString().trim()

            val user = hashMapOf(
                "email" to email,
                "password" to password,
                "nickname" to nickname
            )

            db.collection("users").document()
                .set(user)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    createUser(email, password)
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }
    }

    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
            }
    }

}