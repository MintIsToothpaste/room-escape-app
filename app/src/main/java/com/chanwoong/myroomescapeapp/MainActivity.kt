package com.chanwoong.myroomescapeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.chanwoong.myroomescapeapp.adapter.GridRecyclerViewAdapter
import com.chanwoong.myroomescapeapp.databinding.ActivityMainBinding
import com.chanwoong.myroomescapeapp.model.data.buttonGridItemList
import com.chanwoong.myroomescapeapp.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var gridRecyclerViewAdapter: GridRecyclerViewAdapter
    private val viewModel by viewModels<MainActivityViewModel>()
    private var isRunning = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.setGridItems(buttonGridItemList)

        //binding.editText.setSelection(2)

        gridRecyclerView.apply {
            gridRecyclerViewAdapter = GridRecyclerViewAdapter()
            layoutManager = GridLayoutManager(this@MainActivity, 5)
            adapter = gridRecyclerViewAdapter

            binding.gridRecyclerView.setHasFixedSize(true)
        }

        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.gridItemList.observe(this, Observer {gridItemList->
            gridRecyclerViewAdapter.submitList(gridItemList)
        })
    }

    /*private fun initViewPager2() {

    }*/

    override fun onPause() {
        super.onPause()
        isRunning = false
    }

    override fun onResume() {
        super.onResume()
        isRunning = true
    }

}