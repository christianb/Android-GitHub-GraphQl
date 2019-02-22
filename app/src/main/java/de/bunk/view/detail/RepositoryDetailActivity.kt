package de.bunk.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import de.bunk.util.ImageProvider
import de.bunk.view.R
import kotlinx.android.synthetic.main.activity_repository_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val OWNER = "owner"
private const val REPO_NAME = "repoName"

class RepositoryDetailActivity : AppCompatActivity() {

    private val repositoryDetailViewModel: RepositoryDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_detail)

        repositoryDetailViewModel.liveData().observe(
            this,
            Observer { repositoryItem ->
                detailsNameTextView.text = repositoryItem.name
                starsTextView.text = String.format(getString(R.string.stars), repositoryItem.starCount)
//                urlTextView.text = repositoryItem.url
                descriptionTextView.text = repositoryItem.description
                languageTextView.text = repositoryItem.primaryLanguage
//                forksTextView.text = String.format(getString(R.string.forks), repositoryItem.forks)
//                openIssuesTextView.text = String.format(getString(R.string.open_issues), repositoryItem.openIssues)

                repositoryItem.avatarUrl?.let {
                    ImageProvider.Builder().url(it).into(avatarImageView)
                }
            }
        )

        val owner = intent.getStringExtra(OWNER)
        val repoName = intent.getStringExtra(REPO_NAME)

        repositoryDetailViewModel.loadRepositoryDetails(owner, repoName)
    }

    companion object {
        fun createIntent(
            context: Context,
            owner: String,
            repoName: String
        ) = Intent(context, RepositoryDetailActivity::class.java).apply {
            putExtra(OWNER, owner)
            putExtra(REPO_NAME, repoName)
        }
    }
}