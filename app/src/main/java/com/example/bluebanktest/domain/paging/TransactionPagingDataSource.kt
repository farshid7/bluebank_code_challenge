package com.example.bluebanktest.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bluebanktest.domain.entity.Transaction
import com.example.bluebanktest.domain.repo.TransactionRepository
import com.example.bluebanktest.domain.repo.TransactionRepositoryImpl
import com.example.bluebanktest.ui.main.MainViewModel

class TransactionPagingDataSource(private val repository: TransactionRepository) : PagingSource<Int, Transaction>() {

    override fun getRefreshKey(state: PagingState<Int, Transaction>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
        val pageNumber = params.key ?: 0

        val data = repository.getTransactions(pageNumber)

        return LoadResult.Page(
            data = data,
            prevKey = null,
            nextKey = if (data.size == MainViewModel.PAGE_SIZE) pageNumber + 1 else null
        )
    }
}