package com.example.mycourse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycourse.R
import com.example.mycourse.databinding.AdapterFilterBinding

class FilterAdapter(
    private var onClickListener: (String, Int) -> Unit
) :
    RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {
    private var list = ArrayList<String>()
    private var selectedPositions = HashSet<Int>() // Track selected positions
    private var originalList = ArrayList<String>()

    fun setList(title: List<String>) {
        list.clear()
        list.addAll(title)
        originalList.clear()
        originalList.addAll(title) // Store original names
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterFilterBinding.inflate(inflater, parent, false)
        return FilterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    inner class FilterViewHolder(private val binding: AdapterFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String, position: Int) {
            binding.apply {

                filterName.text = data

                if (selectedPositions.contains(position)) {
                    filterClick.setBackgroundResource(R.drawable.bg_filterchange)
                } else {
                    filterClick.setBackgroundResource(android.R.color.transparent)
                }

                filterClick.setOnClickListener {
                    onClickListener(data, position)
                }
            }
        }
    }

    fun toggleSelection(position: Int) {
        if (selectedPositions.contains(position)) {
            selectedPositions.remove(position) // Deselect if already selected
        } else {
            selectedPositions.add(position) // Select if not selected
        }
        notifyDataSetChanged() // Update view
    }

    fun clearAll() {
        clearSelection()
    }

    private fun clearSelection() {
        selectedPositions.clear() // Clear all selected positions
        notifyDataSetChanged() // Update view
    }

    fun setItemName(position: Int, newName: String) {
        if (position >= 0 && position < list.size) {
            val originalName = originalList[position]
            val updatedName = "$originalName: $newName"
            list[position] = updatedName
            notifyItemChanged(position)
        }
    }

    fun setDefaultName(position: Int, newName: String) {
        if (position >= 0 && position < list.size) {
            val originalName = originalList[position]
            // Set different default names based on the position
            val defaultName = if (position == 0) {
                "$originalName: $newName"
            } else {
                "$originalName: All"
            }
            list[position] = defaultName
            notifyItemChanged(position)
        }
    }
}