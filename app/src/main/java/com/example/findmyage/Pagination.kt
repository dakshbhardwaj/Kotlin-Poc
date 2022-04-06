package com.example.findmyage

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findmyage.Adapter.adapter
import com.example.findmyage.Adapter.newsAdapter
import com.example.findmyage.network.api
import com.example.findmyage.viewModel.repository
import com.example.findmyage.viewModel.viewModalFactory
import com.example.findmyage.viewModel.viewModel
import kotlinx.android.synthetic.main.recycler_view.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class Pagination : AppCompatActivity() {

    lateinit var viewModel: viewModel;
    private val adapter = newsAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view);

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        val ap = api()
        val repo = repository(ap)
        val factory = viewModalFactory(repo)

        viewModel = ViewModelProvider(this, factory).get( com.example.findmyage.viewModel.viewModel::class.java)
        initAdapter()
        loadData();

        tryAgain.setOnClickListener {
            adapter.retry()
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            viewModel.getModalNews().collectLatest { pageData ->
                Log.i("pageData", "$pageData")
                adapter.submitData(pageData)
            }
        }

        lifecycleScope.launch{
            adapter.loadStateFlow.distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { recyclerView.scrollToPosition(0) }
        }

    }

    private fun initAdapter() {
        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = adapter { adapter.retry() },
            footer = adapter { adapter.retry() },
        )
        adapter.addLoadStateListener { loadState ->
            recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            tryAgain.isVisible = loadState.source.refresh is LoadState.Error

        }
    }
}