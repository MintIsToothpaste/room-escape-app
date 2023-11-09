package com.chanwoong.myroomescapeapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanwoong.myroomescapeapp.adapter.MyPostRecyclerViewAdapter
import com.chanwoong.myroomescapeapp.databinding.ActivityMyPostBinding
import com.chanwoong.myroomescapeapp.viewmodels.MyPost
import com.chanwoong.myroomescapeapp.viewmodels.MyPostViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPostBinding
    private val viewModel by viewModels<MyPostViewModel>()

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    override fun onStart() {
        super.onStart()
        attachSnapshotListener()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            initMyPostRecyclerView()
        }, 900)

        // LiveData의 value의 변경을 감지하고 호출
        viewModel.itemsListData.observe(this, Observer {
            binding.myPostReclerView.adapter?.notifyDataSetChanged()
        })

    }

    private fun initMyPostRecyclerView(){
        binding.myPostReclerView.adapter = MyPostRecyclerViewAdapter(viewModel)
        binding.myPostReclerView.layoutManager = LinearLayoutManager(this)
        binding.myPostReclerView.setHasFixedSize(true)

        // 구분선 넣기
        val dividerItemDecoration =
            DividerItemDecoration(this, LinearLayoutManager(this).orientation)

        binding.myPostReclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun attachSnapshotListener(){
        val users = Firebase.auth.currentUser
        val docRef = db.collection("posting")

        if (users != null) {
            docRef.whereEqualTo("uid", users.uid)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if (document != null) {
                            val post = document["post"].toString()
                            val nickname = document["nickname"].toString()
                            val title = document["title"].toString()

                            val item = MyPost(
                                title,
                                post,
                                nickname
                            )

                            Log.d(ContentValues.TAG, item.toString())

                            viewModel.addItem(item)
                        } else {
                            Log.d(ContentValues.TAG, "DocumentSnapshot null")
                        }
                    }
                }
                .addOnFailureListener {
                    Log.d(ContentValues.TAG, "DocumentSnapshot fail")
                }
        }

    }
}