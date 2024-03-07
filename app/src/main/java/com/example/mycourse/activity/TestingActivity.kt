package com.example.mycourse.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycourse.adapter.TestAdapter
import com.example.mycourse.databinding.ActivityPreviewBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * This is a Javadoc comment for the PreviewActivity class.
 * Describe the purpose of the class, its functionalities, and any other relevant information.
 */
@AndroidEntryPoint
class TestingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreviewBinding
    private lateinit var testAdapter: TestAdapter
    private var mList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mList = mutableListOf("Jay", "Joshi", "Agresh", "Priyal")
        initData()
        postInitView()
        loadData()
        manageClicks()
    }

    /**
     * Initialize data for the activity.
     * Set up header layout and adapters.
     */
    //region To init data
    private fun initData() {
        binding.apply {
            testAdapter = TestAdapter(mList)
        }
    }

    /**
     * Perform additional setup after initializing views.
     * Set up RecyclerView and adapters.
     */
    private fun postInitView() {
        binding.apply {
            recyclerViewList.apply {
                adapter = testAdapter
                layoutManager = LinearLayoutManager(this@TestingActivity)
            }
        }
    }//endregion

    /**
     * Load data from intent and display it in RecyclerView.
     */
    //region To load data
    private fun loadData() {}//endregion

    /**
     * Manage click events for various UI elements.
     * Handle back button and search functionality.
     */
    //region To manage click events
    private fun manageClicks() {}//endregion

}