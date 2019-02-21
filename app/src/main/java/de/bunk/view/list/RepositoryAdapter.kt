package de.bunk.view.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import de.bunk.domain.Repository

class RepositoryAdapter : ListAdapter<Repository, RepositoryViewHolder>(DIFF_UTIL_ITEM_CALLBACK) {

//  lateinit var repositoryItemClickListener: RepositoryItemClickListener

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
    return RepositoryViewHolder(parent)
  }

  override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
    holder.bindData(getItem(position))
  }

  companion object {
    private val DIFF_UTIL_ITEM_CALLBACK =
        object : DiffUtil.ItemCallback<Repository>() {
          override fun areItemsTheSame(oldItem: Repository, newItem: Repository) =
              oldItem.id == newItem.id

          override fun areContentsTheSame(oldItem: Repository, newItem: Repository) =
              oldItem == newItem
        }
  }
}