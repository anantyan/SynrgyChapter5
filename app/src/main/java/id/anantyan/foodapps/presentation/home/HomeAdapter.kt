package id.anantyan.foodapps.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import coil.load
import coil.size.ViewSizeResolver
import id.anantyan.foodapps.R
import id.anantyan.foodapps.data.remote.model.ResultsItem
import id.anantyan.foodapps.databinding.ListItemHomeBinding

class HomeAdapter :
    ListAdapter<ResultsItem, HomeAdapter.ResultsItemViewHolder>(ResultsItemComparator) {

    private var _onClick: ((position: Int, item: ResultsItem) -> Unit)? = null

    private object ResultsItemComparator : DiffUtil.ItemCallback<ResultsItem>() {
        override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsItemViewHolder {
        return ResultsItemViewHolder(
            ListItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ResultsItemViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ResultsItemViewHolder(private val binding: ListItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }
        }

        fun bindItem(item: ResultsItem) {
            binding.imgHeadline.load(item.image) {
                crossfade(true)
                placeholder(R.drawable.img_loading)
                error(R.drawable.img_not_found)
                size(ViewSizeResolver(binding.imgHeadline))
            }
            binding.txtServings.text = item.servings.toString()
            binding.txtTitle.text = item.title.toString()
            binding.txtReadyInMinutes.text = item.readyInMinutes.toString()
        }
    }

    fun onClick(listener: (position: Int, item: ResultsItem) -> Unit) {
        _onClick = listener
    }
}