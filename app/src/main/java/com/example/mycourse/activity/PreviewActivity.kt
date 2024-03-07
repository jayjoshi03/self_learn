package com.example.mycourse.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycourse.R
import com.example.mycourse.adapter.BottomSheetAdapter
import com.example.mycourse.adapter.CourseListAdapter
import com.example.mycourse.adapter.FilterAdapter
import com.example.mycourse.databinding.ActivityPreviewBinding
import com.example.mycourse.model.Index
import com.example.mycourse.model.MyCourse
import com.example.mycourse.utilities.Default
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

/**
 * This is a Javadoc comment for the PreviewActivity class.
 * Describe the purpose of the class, its functionalities, and any other relevant information.
 */
@AndroidEntryPoint
class PreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreviewBinding
    private lateinit var courseAdapter: CourseListAdapter
    private var courseList: MyCourse? = null
    private var collection: List<Int>? = null
    private var title = ""
    private lateinit var dialog: BottomSheetDialog
    private lateinit var filterAdapter: FilterAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomSheetAdapter: BottomSheetAdapter
    private lateinit var titleDialog: TextView
    private lateinit var filterSharedPreferences: SharePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = intent.extras!!.getString(Default.TITLE, "")

        if (intent.extras?.getParcelable<MyCourse>(Default.COURSE_LIST) != null) {
            courseList =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) intent.extras?.getParcelable(
                    Default.COURSE_LIST,
                    MyCourse::class.java
                )!!
                else intent.extras?.getParcelable(Default.COURSE_LIST)!!
        }

        collection = intent.getIntegerArrayListExtra(Default.COURSE_COLLECTION)
        filterSharedPreferences = SharePreference(this)
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
            headerLayout.textViewHeaderName.text = title
        }
        courseAdapter = CourseListAdapter()
        filterAdapter = FilterAdapter { s, i ->
            showBottomDialog(s, i)
        }
    }

    /**
     * Perform additional setup after initializing views.
     * Set up RecyclerView and adapters.
     */
    private fun postInitView() {
        binding.apply {
            recyclerViewList.apply {
                adapter = courseAdapter
                layoutManager = GridLayoutManager(this@PreviewActivity, 2)
            }
            recyclerFilterView.apply {
                adapter = filterAdapter
                layoutManager =
                    LinearLayoutManager(this@PreviewActivity, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }//endregion

    /**
     * Load data from intent and display it in RecyclerView.
     */
    //region To load data
    private fun loadData() {
        val courseDataList = filterCourseList(courseList!!.result!!.index, collection!!)
        if (courseDataList.isNotEmpty()) {
            binding.recyclerViewList.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.GONE

            courseAdapter.apply {
                setList(courseDataList)
            }
        } else {
            // Show a message in the RecyclerView indicating no data found
            binding.recyclerViewList.visibility = View.GONE
            binding.tvNoData.visibility = View.VISIBLE
        }


        filterAdapter.apply {
            val filterName =
                mutableListOf(
                    "Only Show Owned",
                    "Skill",
                    "Curriculum",
                    "Style",
                    "Educator",
                    "Series"
                )
            setList(filterName)

            for (i in 0..5) {
                setDefaultName(i, "No")
            }
        }
    }//endregion

    /**
     * Manage click events for various UI elements.
     * Handle back button and search functionality.
     */
    //region To manage click events
    private fun manageClicks() {
        binding.apply {
            headerLayout.imageViewBack.setOnClickListener {
                startActivity(Intent(this@PreviewActivity, MainActivity::class.java))
                filterSharedPreferences.clearAllFilterValues()
            }

            searchProduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    courseAdapter.getFilter().filter(newText)
                    return true
                }
            })
        }

    }//endregion


    /**
     * Function to filter the list of courses based on their IDs.
     * @param indexList List of all courses.
     * @param courseIds List of course IDs to filter.
     * @return List of filtered courses.
     */
    private fun filterCourseList(indexList: List<Index>, courseIds: List<Int>): List<Index> {
        return indexList.filter { courseIds.contains(it.id) }
    }

    /**
     * Function to filter the list of courses based on filter criteria.
     * @param indexList List of all courses.
     * @param filterCriteria Map containing filter criteria.
     * @return List of filtered courses.
     */
    private fun filter(
        indexList: List<Index>,
        filterSharedPreferences: SharePreference
    ): List<Index> {
        val owned = filterSharedPreferences.getFilterValue("owned", "")
        val styleTags = filterSharedPreferences.getFilterValue("styleTags", "")
        val skillTags = filterSharedPreferences.getFilterValue("skillTags", "")
        val curriculum = filterSharedPreferences.getFilterValue("curriculum", "")
        val educator = filterSharedPreferences.getFilterValue("educator", "")
        val series = filterSharedPreferences.getFilterValue("series", "")
        val changeOW = if (owned == "Yes") 1 else 0

        return indexList.filter { index ->
            (styleTags.isEmpty() || index.styleTags.contains(styleTags)) &&
                    (skillTags.isEmpty() || index.skillTags.contains(skillTags)) &&
                    (curriculum.isEmpty() || index.curriculumTags.contains(curriculum)) &&
                    (series.isEmpty() || index.seriesTags.contains(series)) &&
                    (educator.isEmpty() || index.educator!!.contains(
                        educator,
                        ignoreCase = true
                    )) &&
                    (changeOW == 0 || index.owned == changeOW)
        }
    }


    /**
     * Handle back button press.
     * Navigate back to MainActivity.
     */
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@PreviewActivity, MainActivity::class.java))
        filterSharedPreferences.clearAllFilterValues()
    }


    /**
     * Show bottom sheet dialog for filtering courses.
     * @param title Title of the dialog.
     * @param i Index of the filter.
     */
    private fun showBottomDialog(title: String, i: Int) {
        val filterList = filterCourseList(courseList!!.result!!.index, collection!!)
        val owned: ArrayList<String> = arrayListOf("Yes", "No")
        val skill: ArrayList<String> = ArrayList()
        val curriculum: ArrayList<String> = ArrayList()
        val style: ArrayList<String> = ArrayList()
        val educator: ArrayList<String> = arrayListOf(
            "Corey Congilio",
            "David",
            "Freed Haque",
            "Ned Luberecki",
            "Robben Ford",
            "Stu Hamm",
            "TrueFire",
            "Tyler Grant",
            "Jack Ruch",
            "Frank Vignola",
            "Joe Bonamassa",
            "Ariel Posen",
            "Mimi Fox"
        )
        val series: ArrayList<String> = ArrayList()
        for (pos in filterList.indices) {
            skill.addAll(filterList[pos].skillTags)
            curriculum.addAll(filterList[pos].curriculumTags)
            style.addAll(filterList[pos].styleTags)
            series.addAll(filterList[pos].seriesTags)
        }

        val store = arrayListOf(
            owned.distinct(),
            skill.distinct(),
            curriculum.distinct(),
            style.distinct(),
            educator.distinct(),
            series.distinct()
        )
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)
        dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        titleDialog = dialogView.findViewById(R.id.tvSelect)
        titleDialog.text = title
        recyclerView = dialogView.findViewById(R.id.rvItem)
        bottomSheetAdapter = BottomSheetAdapter { s, section ->

            filterSharedPreferences.saveFilterValue(getFilterKey(section), s)

            // Apply filters based on the selected criteria for all sections
            val filterCriteria = mutableMapOf<Int, String?>()
            for (sectionIndex in 0 until store.size) {
                filterCriteria[sectionIndex] =
                    filterSharedPreferences.getFilterValue(getFilterKey(sectionIndex), "")
            }

            val filteredList = filter(filterList, filterSharedPreferences)
            if (filteredList.isNotEmpty()) {
                binding.recyclerViewList.visibility = View.VISIBLE
                binding.tvNoData.visibility = View.GONE
                courseAdapter.apply {
                    setList(filteredList)
                    notifyDataSetChanged()
                }
            } else {
                // Show a message in the RecyclerView indicating no data found
                binding.recyclerViewList.visibility = View.GONE
                binding.tvNoData.visibility = View.VISIBLE
            }

            binding.clearFilter.visibility = View.VISIBLE

            binding.clearFilter.setOnClickListener {
                binding.recyclerViewList.visibility = View.VISIBLE
                binding.tvNoData.visibility = View.GONE
                val filterListClear = filterCourseList(courseList!!.result!!.index, collection!!)
                courseAdapter.apply {
                    setList(filterListClear)
                    notifyDataSetChanged()
                }
                filterAdapter.apply {
                    clearAll()
                    setDefaultName(i, "No")
                }
                binding.clearFilter.visibility = View.GONE
                clearSearch()
                filterSharedPreferences.clearAllFilterValues()
            }

            filterAdapter.setItemName(i, s)
            dialog.dismiss()
        }
        val filterKeys =
            listOf("owned", "styleTags", "skillTags", "curriculum", "educator", "series")
        val filterValues = getFilterValues(filterKeys)
        // Now you can use filterValues map to access the retrieved values
        val ownedCheck = filterValues["owned"] ?: ""
        val styleTagsCheck = filterValues["styleTags"] ?: ""
        val skillTagsCheck = filterValues["skillTags"] ?: ""
        val curriculumCheck = filterValues["curriculum"] ?: ""
        val educatorCheck = filterValues["educator"] ?: ""
        val seriesCheck = filterValues["series"] ?: ""
        val checkList = listOf(
            ownedCheck,
            styleTagsCheck,
            skillTagsCheck,
            curriculumCheck,
            educatorCheck,
            seriesCheck,
            ownedCheck
        )
        bottomSheetAdapter.setList(store[i], i, checkList)
        filterAdapter.toggleSelection(i)
        recyclerView.adapter = bottomSheetAdapter
        dialog.show()
    }

    private fun getFilterKey(sectionIndex: Int): String {
        return when (sectionIndex) {
            0 -> "owned"
            1 -> "skillTags"
            2 -> "curriculum"
            3 -> "styleTags"
            4 -> "educator"
            5 -> "series"
            else -> ""
        }
    }

    // Function to retrieve multiple values from SharedPreferences for a list of keys
    private fun getFilterValues(keys: List<String>): Map<String, String> {
        val filterValues = mutableMapOf<String, String>()
        for (key in keys) {
            val value = filterSharedPreferences.getFilterValue(key, "")
            filterValues[key] = value
        }
        return filterValues
    }

    private fun clearSearch() {
        binding.apply {
            searchProduct.apply {
                setQuery("", false)
                clearFocus()
            }
        }
    }

}