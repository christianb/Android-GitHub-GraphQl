package de.bunk.view.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.bunk.domain.repository.PagedRepository
import de.bunk.view.R
import kotlinx.android.synthetic.main.repository_viewholder.view.*

class RepositoryViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.repository_viewholder, parent, false)
) {
    fun bindData(item: PagedRepository, repositoryItemClickListener: RepositoryItemClickListener?) {
        val repository = item.repository

        itemView.nameTextView.text = repository.name

        repository.primaryLanguage?.let {
            itemView.languageTextView.text = String.format(itemView.context.getString(R.string.language), it)
            itemView.languageTextView.visibility = View.VISIBLE
        }

        itemView.starsTextView.text = String.format(itemView.context.getString(R.string.stars), repository.starCount)

        itemView.setOnClickListener {
            repositoryItemClickListener?.onItemClicked(item)
        }
    }
}