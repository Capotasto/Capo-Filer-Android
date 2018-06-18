package com.funckyhacker.capofiler.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.funckyhacker.capofiler.databinding.ItemMainLinearBinding
import com.funckyhacker.capofiler.event.ClickItemEvent
import com.funckyhacker.capofiler.util.DateUtils
import org.greenrobot.eventbus.EventBus
import java.io.File

class MainLinearAdapter : RecyclerView.Adapter<MainLinearAdapter.ViewHolder>() {

    private var files: List<File>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val linearBinding = ItemMainLinearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(linearBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(files!![position])
    }

    override fun getItemCount(): Int {
        return if (files == null) 0 else files!!.size
    }

    fun setData(files: List<File>?) {
        this.files = files
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemMainLinearBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var file: File

        val modified: String
            get() = DateUtils.getItemDate(file.lastModified())

        fun bind(file: File) {
            this.file = file
            binding.file = file
            binding.viewHolder = this
            binding.executePendingBindings()
        }

        fun onClickItem() {
            EventBus.getDefault().post(ClickItemEvent(file))
        }
    }
}
