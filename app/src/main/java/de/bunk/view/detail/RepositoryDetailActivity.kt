package de.bunk.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.bunk.view.R

private const val OWNER = "owner"
private const val REPO_NAME = "repoName"

class RepositoryDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_detail)
    }

    companion object {
        fun intent(
            context: Context,
            owner: String,
            repoName: String
        ) = Intent(context, RepositoryDetailActivity::class.java).apply {
            putExtra(OWNER, owner)
            putExtra(REPO_NAME, repoName)
        }
    }
}