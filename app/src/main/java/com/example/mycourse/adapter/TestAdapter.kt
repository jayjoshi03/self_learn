package com.example.mycourse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycourse.R
import com.example.mycourse.databinding.AdapterTestingBinding

class TestAdapter(
    private val mList: MutableList<String>
) :
    RecyclerView.Adapter<TestAdapter.TestViewHolder>() {
    private lateinit var binding: AdapterTestingBinding
    private var selectedPosition = 0

    inner class TestViewHolder(private val bind: AdapterTestingBinding) :
        RecyclerView.ViewHolder(bind.root) {

        init {
            bind.layoutClick.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    setSelectedPosition(position)
                }
            }
        }

        fun bind(data: String, position: Int) {
            bind.apply {
                testView.text = data

                if (position == selectedPosition) {
                    layoutClick.setBackgroundResource(R.color.blue)
                } else {
                    layoutClick.setBackgroundResource(R.color.lightGray)
                }
            }
        }
    }

    private fun setSelectedPosition(position: Int) {
        if (selectedPosition != RecyclerView.NO_POSITION) {
            notifyItemChanged(selectedPosition)
        }
        selectedPosition = position
        notifyItemChanged(selectedPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        binding = AdapterTestingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TestViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.bind(mList[position], position)
    }
}
