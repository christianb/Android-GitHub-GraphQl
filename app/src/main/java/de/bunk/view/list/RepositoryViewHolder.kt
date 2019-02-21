package de.bunk.view.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.bunk.domain.Repository
import de.bunk.view.R
import kotlinx.android.synthetic.main.repository_viewholder.view.*

class RepositoryViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.repository_viewholder, parent, false)
) {
    fun bindData(item: Repository) {
        itemView.nameTextView.text = item.name

        item.primaryLanguage?.let {
            itemView.languageTextView.text = String.format(itemView.context.getString(R.string.language), it)
            itemView.languageTextView.visibility = View.VISIBLE
        }

        itemView.starsTextView.text = String.format(itemView.context.getString(R.string.stars), item.starCount)
//
//    itemView.setOnClickListener {
//      repositoryItemClickListener.onItemClick(item)
//    }
    }
}