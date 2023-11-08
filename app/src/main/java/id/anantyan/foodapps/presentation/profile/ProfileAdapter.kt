package id.anantyan.foodapps.presentation.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import id.anantyan.foodapps.databinding.ListItemProfileBinding

class ProfileAdapter : ListAdapter<ProfileItemModel, ProfileAdapter.ProfileItemModelViewHolder>(
    ProfileItemModelComparator
) {

    private object ProfileItemModelComparator : DiffUtil.ItemCallback<ProfileItemModel>() {
        override fun areItemsTheSame(
            oldItem: ProfileItemModel,
            newItem: ProfileItemModel
        ): Boolean {
            return oldItem.resId == newItem.resId
        }

        override fun areContentsTheSame(
            oldItem: ProfileItemModel,
            newItem: ProfileItemModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileItemModelViewHolder {
        return ProfileItemModelViewHolder(
            ListItemProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProfileItemModelViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ProfileItemModelViewHolder(private val binding: ListItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: ProfileItemModel) {
            binding.imageView.setImageResource(item.resId ?: 0)
            binding.txtTitle.text = binding.root.context.getString(item.resStr ?: 0)
            binding.txtField.text = item.field
        }
    }
}