package de.bunk.view.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import de.bunk.domain.repository.PagedRepository

class RepositoryAdapter :
    ListAdapter<PagedRepository, RepositoryViewHolder>(DIFF_UTIL_ITEM_CALLBACK),
    RepositoryItemClickListener {

    var repositoryItemClickListener: RepositoryItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bindData(getItem(position), this)
    }

    override fun onItemClicked(pagedRepository: PagedRepository) {
        repositoryItemClickListener?.onItemClicked(pagedRepository)
    }

    companion object {
        private val DIFF_UTIL_ITEM_CALLBACK =
            object : DiffUtil.ItemCallback<PagedRepository>() {
                override fun areItemsTheSame(oldItem: PagedRepository, newItem: PagedRepository) =
                    oldItem.repository.id == newItem.repository.id

                override fun areContentsTheSame(oldItem: PagedRepository, newItem: PagedRepository) = oldItem == newItem
        }
  }
}