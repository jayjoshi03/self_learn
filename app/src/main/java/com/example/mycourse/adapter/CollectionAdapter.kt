package com.example.mycourse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycourse.databinding.AdapterCourseBinding
import com.example.mycourse.model.Index
import com.example.mycourse.model.Smart

class CollectionAdapter(
    private var onClickListener: (Smart, Int) -> Unit
) :
    RecyclerView.Adapter<CollectionAdapter.CourseViewHolder>() {
    private var list = ArrayList<Smart>()
    private var courseList = ArrayList<Index>()
    private lateinit var courseListAdapter: CourseListAdapter


    fun setList(title: List<Smart>) {
        list.clear()
        list.addAll(title)
        notifyItemRangeChanged(0, list.size)
    }

    fun setCourseList(title: List<Index>) {
        courseList.clear()
        courseList.addAll(title)
        notifyDataSetChanged()
    }

    // Function to filter the list of courses based on their IDs
    fun filterCoursesById(indexList: List<Index>, courseIds: List<Int>): List<Index> {
        return indexList.filter { courseIds.contains(it.id) }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterCourseBinding.inflate(inflater, parent, false)
        return CourseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    inner class CourseViewHolder(private val binding: AdapterCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Smart, position: Int) {
            binding.apply {
                collectionView.text = data.label.toString()
                courseListAdapter = CourseListAdapter()
                nestedRecyclerView.apply {
                    layoutManager =
                        LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
                    adapter = courseListAdapter
                }
                val filterIndex = filterCoursesById(courseList, data.courses)
                courseListAdapter.setList(filterIndex)

                btnViewMore.setOnClickListener {
                    onClickListener(data, position)
                }
            }
        }
    }
}