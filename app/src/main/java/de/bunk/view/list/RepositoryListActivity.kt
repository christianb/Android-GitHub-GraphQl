package de.bunk.view.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import de.bunk.domain.Repository
import de.bunk.view.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoryListActivity : AppCompatActivity() {

    private val repositoryListViewModel: RepositoryListViewModel by viewModel()

    private val repositoryAdapter = RepositoryAdapter().apply {
//        repositoryItemClickListener = object : RepositoryItemClickListener {
//            override fun onItemClick(repositoryItem: RepositoryItem) {
//                val context = this@RepositoryListActivity
//                val intent = RepositoryDetailActivity.createIntent(context, repositoryItem.id)
//                context.startActivity(intent)
//            }
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(recyclerView) {
            adapter = repositoryAdapter
            layoutManager = LinearLayoutManager(this@RepositoryListActivity)
//            addItemDecoration(VerticalSpaceItemDecoration(VERTICAL_SPACE_HEIGHT))
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
//            addOnScrollListener(paginationScrollListener)
        }

        repositoryListViewModel.liveData().observe(this,
            Observer<List<Repository>> {
                repositoryAdapter.submitList(it)
            }
        )

        repositoryListViewModel.fetchRepositories()
    }
}
