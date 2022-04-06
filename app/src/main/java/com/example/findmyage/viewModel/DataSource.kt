package com.example.findmyage.viewModel

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.findmyage.models.New
import com.example.findmyage.network.api
import retrofit2.HttpException
import java.io.IOException


class DataSource(private val services: api) : PagingSource<Int, New>() {
    override fun getRefreshKey(state: PagingState<Int, New>): Int? {
      return state.anchorPosition;
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, New> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = services.getResult(nextPageNumber)
            val data = response.news
            Log.i("data getting", "$data")
            LoadResult.Page(
                data = data,
                prevKey = if(nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if(data.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: IOException){
            LoadResult.Error(e)
        } catch (e: HttpException){
            LoadResult.Error(e)
        }
    }

}