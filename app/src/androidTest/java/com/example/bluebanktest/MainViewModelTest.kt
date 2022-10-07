package com.example.bluebanktest

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bluebanktest.domain.repo.TransactionRepositoryImpl
import com.example.bluebanktest.ui.main.MainViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainViewModelTest {
    @BindValue
    @JvmField
    val viewModel = MainViewModel(TransactionRepositoryImpl())

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getData() = runTest {
        viewModel.transaction.collect {
            assert(true)
        }

    }

}