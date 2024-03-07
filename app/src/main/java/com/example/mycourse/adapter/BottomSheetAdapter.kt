package com.example.mycourse.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycourse.databinding.ListItemBinding

class BottomSheetAdapter(
    private var onClickListener: (String, Int) -> Unit
) :
    RecyclerView.Adapter<BottomSheetAdapter.BottomSheetViewHolder>() {
    private var list = ArrayList<String>()
    private var sectionIdentifier: Int = -1
    private var selectedItems: List<String>? = null

    fun setList(title: List<String>, section: Int, selectedItems: List<String>?) {
        list.clear()
        list.addAll(title)
        sectionIdentifier = section
        this.selectedItems = selectedItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return BottomSheetViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BottomSheetViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    inner class BottomSheetViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String, position: Int) {
            binding.apply {
                tvFName.text = data

                // Set the checkmark visibility based on whether the current item is selected
                ivCheckmark.visibility = if (selectedItems?.contains(data) == true) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                // Set click listener for the root view of each item
                itemView.setOnClickListener {
                    // Call onItemClick with the clicked item
                    onClickListener(data, sectionIdentifier)
                }
            }
        }
    }
}