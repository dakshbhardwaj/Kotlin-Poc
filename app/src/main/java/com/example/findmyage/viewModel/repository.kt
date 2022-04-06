package com.example.findmyage.viewModel

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.findmyage.models.New

import com.example.findmyage.network.api
import kotlinx.coroutines.flow.Flow

class repository(private val ap:api) {

    fun getResult(): Flow<PagingData<New>>{
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = {DataSource(ap)}
        ).flow
    }
}