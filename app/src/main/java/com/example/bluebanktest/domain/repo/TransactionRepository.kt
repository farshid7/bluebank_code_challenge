package com.example.bluebanktest.domain.repo

import com.example.bluebanktest.domain.entity.Transaction

interface TransactionRepository {
    fun getTransactions(page: Int): List<Transaction>
}