package com.example.bluebanktest.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.bluebanktest.domain.paging.TransactionPagingDataSource
import com.example.bluebanktest.domain.repo.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: TransactionRepository): ViewModel() {


    val transaction =
        Pager(config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = { TransactionPagingDataSource(repository) }
        ).flow.cachedIn(viewModelScope)


    companion object{
        const val PAGE_SIZE=20
    }
}