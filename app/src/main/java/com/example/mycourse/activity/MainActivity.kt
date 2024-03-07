package com.example.mycourse.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycourse.R
import com.example.mycourse.adapter.CollectionAdapter
import com.example.mycourse.databinding.ActivityMainBinding
import com.example.mycourse.model.MyCourse
import com.example.mycourse.utilities.Default
import com.example.mycourse.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This is a Javadoc comment for the MainActivity class.
 * Describe the purpose of the class, its functionalities, and any other relevant information.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var collectionAdapter: CollectionAdapter
    private var courseList: MyCourse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        postInitView()
        loadData()
    }

    /**
     * Initialize data for the activity.
     * Initialize courseList and setup header layout.
     */
    //region To init data
    private fun initData() {
        courseList = MyCourse()
        binding.apply {
            headerLayout.textViewHeaderName.text = resources.getString(R.string.app_name)
            headerLayout.imageViewBack.visibility = View.INVISIBLE
        }

        collectionAdapter = CollectionAdapter { collection, _ ->
            val intent = Intent(this, PreviewActivity::class.java)
            intent.putExtra(Default.TITLE, collection.label)
            intent.putExtra(Default.COURSE_LIST, courseList)
            intent.putIntegerArrayListExtra(Default.COURSE_COLLECTION, collection.courses as ArrayList<Int>)
            startActivity(intent)
        }
    }

    /**
     * Perform additional setup after initializing views.
     * Set up RecyclerView and its adapter.
     */
    private fun postInitView() {
        binding.apply {
            recyclerView.apply {
                adapter = collectionAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
            }

        }
    }//endregion

    /**
     * Load data from ViewModel.
     * Observe ViewModel LiveData and update UI accordingly.
     */
    //region To load data
    private fun loadData() {
        viewModel.fetchCurseDetails().observe(this) {
            CoroutineScope(Dispatchers.Main).launch {
                courseList = it
                collectionAdapter.apply {
                    setList(it.result!!.collections!!.smart)
                    setCourseList(it.result!!.index)
                    notifyDataSetChanged()
                }
            }
        }
    }//endregion
}