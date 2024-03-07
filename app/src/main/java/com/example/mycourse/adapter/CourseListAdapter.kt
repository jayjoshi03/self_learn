package com.example.mycourse.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mycourse.R
import com.example.mycourse.databinding.AdapterCourseListBinding
import com.example.mycourse.model.Index

class CourseListAdapter :
    RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder>() {
    private var list = ArrayList<Index>()
    private var filteredList = ArrayList<Index>()

    fun setList(title: List<Index>) {
        list.clear()
        list.addAll(title)
        filteredList.clear()
        filteredList.addAll(title)
        notifyItemRangeChanged(0, filteredList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterCourseListBinding.inflate(inflater, parent, false)
        return CourseListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: CourseListViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    //region To get filtered data
    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val searchString = charSequence.toString().lowercase()
                filteredList = if (searchString.isEmpty()) {
                    list
                } else {
                    val tempFilteredList = ArrayList<Index>()
                    for (course in list) { // search for user title
                        if (course.title!!.lowercase().contains(searchString)) tempFilteredList.add(
                            course
                        )
                    }
                    tempFilteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                filteredList = filterResults.values as ArrayList<Index>
                notifyDataSetChanged()
            }
        }
    } //endregion

    inner class CourseListViewHolder(private val binding: AdapterCourseListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Index) {
            binding.apply {
                ownedLayout.visibility = if (data.owned == 1) View.VISIBLE else View.GONE
                try {
                    Glide.with(itemView.context)
                        .load("https://d2xkd1fof6iiv9.cloudfront.net/images/courses/${data.id}/169_820.jpg")
                        .error(R.drawable.ic_category)
                        .into(imageView)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
                courseTitle.text = if (data.title!!.isNotEmpty()) data.title else ""
                courseEducator.text = if (data.educator!!.isNotEmpty()) data.educator else ""
            }
        }
    }
}