package com.example.bluebanktest

import com.example.bluebanktest.domain.repo.TransactionRepository
import com.example.bluebanktest.domain.repo.TransactionRepositoryImpl
import com.example.bluebanktest.ui.main.MainViewModel
import org.junit.Assert
import org.junit.Test

class TransactionRepositoryTest {

    private val repo:TransactionRepository=TransactionRepositoryImpl()
    @Test
    fun get_data_from_repo() {
        val data=repo.getTransactions(1)
        Assert.assertEquals(data.size, MainViewModel.PAGE_SIZE)
    }

}