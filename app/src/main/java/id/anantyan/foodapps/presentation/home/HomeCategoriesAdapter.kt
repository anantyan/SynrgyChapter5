package id.anantyan.foodapps.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import id.anantyan.foodapps.databinding.ListItemTypeBinding

class HomeCategoriesAdapter :
    ListAdapter<HomeCategoriesModel, HomeCategoriesAdapter.HomeCategoriesModelViewHolder>(
        HomeCategoriesModelComparator
    ) {
    private var _onClick: ((position: Int, item: HomeCategoriesModel) -> Unit)? = null

    private object HomeCategoriesModelComparator : DiffUtil.ItemCallback<HomeCategoriesModel>() {
        override fun areItemsTheSame(
            oldItem: HomeCategoriesModel,
            newItem: HomeCategoriesModel
        ): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(
            oldItem: HomeCategoriesModel,
            newItem: HomeCategoriesModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeCategoriesModelViewHolder {
        return HomeCategoriesModelViewHolder(
            ListItemTypeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeCategoriesModelViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class HomeCategoriesModelViewHolder(val binding: ListItemTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }
        }

        fun bindItem(item: HomeCategoriesModel) {
            binding.root.text = binding.root.context.getString(item.valueResId ?: 0)
        }
    }

    fun onClick(listener: (position: Int, item: HomeCategoriesModel) -> Unit) {
        _onClick = listener
    }
}